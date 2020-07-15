package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.ProductApiClient;
import com.fh.bo.LoginUserInfo;
import com.fh.mapper.IOrderDetailMapper;
import com.fh.mapper.IOrderMapper;
import com.fh.mapper.IPayLogMapper;
import com.fh.po.OrderBeanPo;
import com.fh.po.OrderDetailBeanPo;
import com.fh.po.PayLogBeanPo;
import com.fh.po.ProductBeanPo;
import com.fh.service.IOrderService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import com.fh.vo.CartBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiezhuang
 * @ClassName OrderServiceImpl
 * @date 2020/7/6 20:42
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private IOrderMapper orderMapper;

    @Resource
    private IOrderDetailMapper orderDetailMapper;

    @Resource
    private IPayLogMapper payLogMapper;

    @Resource
    private ProductApiClient productApiClient;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String CART_KEY="cart_";

    public static final Integer ORDER_STATUS_WAIT_PAY=100;



    @Override
    @Transactional
    public ResponseServer createOrder(LoginUserInfo user, Integer addressId) {
        //从redis中取出购物车的数据
        List<CartBean> cartList = redisTemplate.opsForHash().values(CART_KEY + user.getCartId());
        //筛选出选中的购物车数据没有就返回提示信息：没有要支付的订单
        List<CartBean> buyList = cartList.stream().filter(cartBean -> cartBean.isChecked()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(buyList)){
            return ResponseServer.error(ServerEnum.NO_ORDER_TO_PAY);
        }
        //随机生成ID
        String orderId = IdWorker.getIdStr();
        //筛选购物车中的数据是否充足
        List<CartBean> underStockList = new ArrayList<>();
        underStockList = buyList.stream().filter(cartBean -> cartBean.getStock()<cartBean.getNum()).collect(Collectors.toList());
        //判断库存是否充足不充足返回不充足的商品信息进行提示
        if(!CollectionUtils.isEmpty(underStockList)){
            return ResponseServer.error(underStockList);
        }
        //筛选数据库中有没有库存不足的情况
        List<OrderDetailBeanPo> orderDetailBeanPoList = new ArrayList<>();
        underStockList = checkStock(user,underStockList,buyList,orderDetailBeanPoList,orderId);
        if(!CollectionUtils.isEmpty(underStockList)){
            //事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseServer.error(underStockList);
        }
        //购买的总数量和总计金额
        BigDecimal totalMoney = new BigDecimal(0.00);
        BigDecimal totalCount = new BigDecimal(0);
        //保存数据到订单详情表
        for (OrderDetailBeanPo orderDetail:orderDetailBeanPoList){
            totalMoney = totalMoney.add(orderDetail.getSubTotalPrice());
            totalCount = totalCount.add(BigDecimal.valueOf(orderDetail.getCount()));
            orderDetailMapper.insert(orderDetail);
        }
        //保存数据到订单表
        OrderBeanPo orderBeanPo = new OrderBeanPo();
        orderBeanPo.setAddressId(addressId);
        orderBeanPo.setCreateTime(LocalDateTime.now(ZoneId.of("+08:00")));
        orderBeanPo.setUserId(user.getUserId());
        orderBeanPo.setTotalCount(totalCount.intValue());
        orderBeanPo.setTotalPrice(totalMoney);
        orderBeanPo.setId(orderId);
        orderBeanPo.setStatus(ORDER_STATUS_WAIT_PAY);
        orderMapper.insert(orderBeanPo);
        //保存数据到订单支付表
        PayLogBeanPo payLogBeanPo = new PayLogBeanPo();
        payLogBeanPo.setOutTradeNo(IdWorker.getIdStr());
        payLogBeanPo.setCreateTime(LocalDateTime.now(ZoneId.of("+08:00")));
        payLogBeanPo.setOrderId(orderId);
        payLogBeanPo.setPayMoney(totalMoney);
        payLogBeanPo.setUserId(user.getUserId());
        payLogBeanPo.setPayStatus(ORDER_STATUS_WAIT_PAY);
        payLogMapper.insert(payLogBeanPo);
        redisTemplate.opsForHash().put("PATH:ORDER:"+user.getUserId(),payLogBeanPo.getOutTradeNo(),payLogBeanPo);
        //返回数据
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("totalMoney",totalMoney);
        map.put("outTradeNo",payLogBeanPo.getOutTradeNo());
        //订单生成成功将购物车中选中的数据删除
        if(buyList.size() != 0){
            for (int i = 0; i < buyList.size(); i++) {
                CartBean cartBean = buyList.get(i);
                Integer productId = cartBean.getProductId();
                redisTemplate.opsForHash().delete(CART_KEY+user.getCartId(),productId);
            }
        }
        return ResponseServer.success(map);
    }

    private List<CartBean> checkStock(LoginUserInfo user,List<CartBean> underStockList,List<CartBean> buyList,List<OrderDetailBeanPo> orderDetailBeanPoList,String orderId){

        buyList.forEach(cartBean -> {
            ResponseServer response = productApiClient.getProductById(cartBean.getProductId());
            ProductBeanPo product = JSON.parseObject(JSON.toJSONString(response.getData()), ProductBeanPo.class);
            //判断库存是否充足
            if(cartBean.getNum() > product.getStock()){
                cartBean.setStock(product.getStock());
                underStockList.add(cartBean);
            }else{
                int count = orderMapper.updateProductStock(cartBean.getNum(),cartBean.getProductId());
                if(count == 0){
                    underStockList.add(cartBean);
                }else{
                    OrderDetailBeanPo orderDetailBeanPo = new OrderDetailBeanPo();
                    orderDetailBeanPo.setCount(cartBean.getNum());
                    orderDetailBeanPo.setPrice(product.getPrice());
                    orderDetailBeanPo.setUserId(user.getUserId());
                    orderDetailBeanPo.setOrderId(orderId);
                    orderDetailBeanPo.setProductName(product.getName());
                    orderDetailBeanPo.setProductId(product.getId());
                    orderDetailBeanPo.setSubTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartBean.getNum())));
                    orderDetailBeanPoList.add(orderDetailBeanPo);
                }
            }
        });

        return underStockList;
    }

}
