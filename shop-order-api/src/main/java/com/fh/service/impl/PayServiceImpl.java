package com.fh.service.impl;

import com.fh.bo.LoginUserInfo;
import com.fh.github.wxpay.sdk.MyWXConfig;
import com.fh.github.wxpay.sdk.WXPay;
import com.fh.mapper.IOrderMapper;
import com.fh.mapper.IPayLogMapper;
import com.fh.po.OrderBeanPo;
import com.fh.po.PayLogBeanPo;
import com.fh.service.IPayService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezhuang
 * @ClassName PayServiceImpl
 * @date 2020/7/7 12:32
 */
@Service("payService")
public class PayServiceImpl implements IPayService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private IPayLogMapper payLogMapper;

    @Resource
    private IOrderMapper orderMapper;

    public static final Integer ORDER_STATUS_PAY_SUCCESS=250;

    @Override
    public ResponseServer createQy(LoginUserInfo userInfo, String outTradeNo) {
        //从redis中取出数据
        PayLogBeanPo payLog = (PayLogBeanPo) redisTemplate.opsForHash().get("PATH:ORDER:"+userInfo.getUserId(),outTradeNo);
        if(payLog == null){
            return ResponseServer.error(ServerEnum.NO_ORDER_TO_PAY);
        }
        //开始支付
        //初始化配置
        MyWXConfig myWXConfig = new MyWXConfig();
        try {
            WXPay wxPay = new WXPay(myWXConfig);
            Map<String,String> map = new HashMap<>();
            //微信支付头信息
            map.put("body","商品支付");
            //订单号
            map.put("out_trade_no",outTradeNo);
            //货币
            map.put("fee_type","CNY");
            //总金额 分单位
            //payLog.getPayMoney().multiply(BigDecimal.valueOf(100))+""
            map.put("total_fee", 1+"");
            //支付的超时时间设置
            String expireTime= DateFormatUtils.format( DateUtils.addMinutes(new Date(),1),
                    "yyyyMMddHHmmss");
            map.put("time_expire",expireTime);
            //返回的地址
            map.put("notify_url","http://www.example.com/wxpay/notify");
            //支付方式
            map.put("trade_type", "NATIVE");
            Map<String, String> resultMap = wxPay.unifiedOrder(map);
            //判断微信服务是否能访问
            String returnCode=resultMap.get("return_code");
            if(!"SUCCESS".equals(returnCode)){
                return ResponseServer.error(ServerEnum.CRATER_PAY_ERROR);
            }
            //判断微信业务处理是否报错
            String resultCode=resultMap.get("result_code");
            if(!"SUCCESS".equals(resultCode)){
                return ResponseServer.error(ServerEnum.CRATER_PAY_ERROR);
            }
            String qrCode=resultMap.get("code_url");
            Map<String,String> resMap=new HashMap<>();
            resMap.put("outTradeNo",outTradeNo);
            resMap.put("totalMoney",payLog.getPayMoney()+"");
            resMap.put("codeUrl",qrCode);
            return ResponseServer.success(resMap);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseServer.error(ServerEnum.CRATER_PAY_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseServer checkOrderPayStatus(String outTradeNo, LoginUserInfo userInfo) {
        //从redis中取出数据
        PayLogBeanPo payLog = (PayLogBeanPo) redisTemplate.opsForHash().get("PATH:ORDER:"+userInfo.getUserId(),outTradeNo);
        if(payLog == null){
            return ResponseServer.error(ServerEnum.NO_ORDER_TO_PAY);
        }
        int count = 0;
        while (true){
            MyWXConfig myWXConfig = new MyWXConfig();
            try {
                WXPay wxPay = new WXPay(myWXConfig);
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("out_trade_no",outTradeNo);
                Map<String, String> resultMap = wxPay.orderQuery(dataMap);
                //判断微信服务是否能访问
                String returnCode=resultMap.get("return_code");
                if(!"SUCCESS".equals(returnCode)){
                    return ResponseServer.error(ServerEnum.CRATER_PAY_ERROR);
                }
                //判断微信业务处理是否报错
                String resultCode=resultMap.get("result_code");
                if(!"SUCCESS".equals(resultCode)){
                    return ResponseServer.error(ServerEnum.CRATER_PAY_ERROR);
                }

                //获取交易状态
                String tradeState = resultMap.get("trade_state");
                if("SUCCESS".equals(tradeState)){
                    //更新支付日志记录
                    PayLogBeanPo payLogBeanPo = new PayLogBeanPo();
                    payLogBeanPo.setPayTime(LocalDateTime.now());
                    payLogBeanPo.setPayStatus(ORDER_STATUS_PAY_SUCCESS);
                    payLogBeanPo.setOutTradeNo(outTradeNo);
                    payLogBeanPo.setPayType(1);
                    payLogMapper.updateById(payLogBeanPo);
                    //更新订单状态
                    OrderBeanPo orderBeanPo = new OrderBeanPo();
                    orderBeanPo.setStatus(ORDER_STATUS_PAY_SUCCESS);
                    orderBeanPo.setPayTime(LocalDateTime.now());
                    orderBeanPo.setPayType(1);
                    orderBeanPo.setId(payLogBeanPo.getOrderId());
                    orderMapper.updateById(orderBeanPo);
                    return ResponseServer.success();
                }
                count++;
                System.out.println("=================支付状态为==============="+tradeState);
                System.out.println("=================重试次数为================"+count);
                if(count>30){
                    return ResponseServer.error(ServerEnum.PAY_TIMEOUT);
                }
                Thread.sleep(3000L);
            }catch (Exception e){
                //事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
               return ResponseServer.error();
            }
        }
    }
}
