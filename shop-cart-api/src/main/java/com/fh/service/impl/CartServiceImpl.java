package com.fh.service.impl;

import com.alibaba.fastjson.JSON;
import com.fh.ProductApiClient;
import com.fh.bo.LoginUserInfo;
import com.fh.po.ProductBeanPo;
import com.fh.service.ICartService;
import com.fh.utils.ResponseServer;
import com.fh.vo.CartBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName CartServiceImpl
 * @date 2020/7/5 13:06
 */
@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private ProductApiClient productApiClient;
    private static final String CART_KEY="cart_";

    @Override
    public Long addCart(Integer productId, Integer num, String cartId) {
        //查询商品信息
        ProductBeanPo data = getProductById(productId);
        CartBean cartBean = new CartBean();
        cartBean.setNum(num);
        cartBean.setPrice(data.getPrice());
        cartBean.setProductId(data.getId());
        cartBean.setProductName(data.getName());
        cartBean.setStock(data.getStock());
        cartBean.setChecked(false);
        BigDecimal multiply = cartBean.getPrice().multiply(BigDecimal.valueOf(num));
        cartBean.setSubtotal(multiply);
        //判断商品是否已经加入购物车
        CartBean cart = (CartBean) redisTemplate.opsForHash().get(CART_KEY + cartId, productId);
        if(cart != null){
            cartBean.setNum(num+cart.getNum());
            BigDecimal subtotal = cartBean.getPrice().multiply(BigDecimal.valueOf(cartBean.getNum()));
            cartBean.setSubtotal(subtotal);
        }else{
            cartBean.setNum(num);
            BigDecimal subtotal = cartBean.getPrice().multiply(BigDecimal.valueOf(num));
            cartBean.setSubtotal(subtotal);
        }
        //存放到redis中
        redisTemplate.opsForHash().put(CART_KEY+cartId,productId,cartBean);
        Long size = redisTemplate.opsForHash().size(CART_KEY + cartId);
        return size;
    }

    @Override
    public List<CartBean> queryCartList(String cartId) {
        return redisTemplate.opsForHash().values(CART_KEY + cartId);
    }

    @Override
    public Long updateCart(Integer productId, Integer num, String cartId,Integer flag) {
        ProductBeanPo data = getProductById(productId);
        CartBean cartBean = new CartBean();
        cartBean.setNum(num);
        cartBean.setPrice(data.getPrice());
        cartBean.setProductId(data.getId());
        cartBean.setProductName(data.getName());
        cartBean.setStock(data.getStock());
        BigDecimal multiply = cartBean.getPrice().multiply(BigDecimal.valueOf(num));
        cartBean.setSubtotal(multiply);
        //取出该商品
        CartBean cart = (CartBean) redisTemplate.opsForHash().get(CART_KEY + cartId, productId);
        if(flag == 1){
            cartBean.setChecked(cart.isChecked());
        }else{
            cartBean.setChecked(!cart.isChecked());
        }

        cartBean.setNum(num);
        BigDecimal subtotal = cartBean.getPrice().multiply(BigDecimal.valueOf(cartBean.getNum()));
        cartBean.setSubtotal(subtotal);
        //存放到redis中去
        redisTemplate.opsForHash().put(CART_KEY + cartId,productId,cartBean);
        return 1L;
    }

    @Override
    public Long checkedAllProduct(String cartId, Boolean checkAllFlag) {
        List<CartBean> values = redisTemplate.opsForHash().values(CART_KEY + cartId);
        values.forEach(cart->{
            //查询商品信息
            ProductBeanPo product = getProductById(cart.getProductId());
            if(checkAllFlag){
                cart.setChecked(true);
            }else{
                cart.setChecked(false);
            }
            cart.setProductName(product.getName());
            cart.setStock(product.getStock());
            cart.setPrice(product.getPrice());
            BigDecimal multiply = product.getPrice().multiply(BigDecimal.valueOf(cart.getNum()));
            cart.setSubtotal(multiply);
            redisTemplate.opsForHash().put(CART_KEY+cartId,product.getId(),cart);
        });
        return 1L;
    }

    @Override
    public Long deleteProduct(Integer productId, String cartId) {
        //删除redis对应的key
        redisTemplate.opsForHash().delete(CART_KEY+cartId,productId);
        return 1L;
    }

    @Override
    public void delAllCarts(LoginUserInfo userInfo) {
        String cartId = userInfo.getCartId();

        redisTemplate.delete(CART_KEY+cartId);
    }

    private ProductBeanPo getProductById(Integer productId){
        ResponseServer responseServer = productApiClient.getProductById(productId);
        ProductBeanPo data = JSON.parseObject(JSON.toJSONString(responseServer.getData()),ProductBeanPo.class);
        return data;
    }

}
