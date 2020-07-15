package com.fh.service;

import com.fh.bo.LoginUserInfo;
import com.fh.vo.CartBean;

import java.util.List;

/**
 * @author xiezhuang
 * @ClassName ICartService
 * @date 2020/7/5 13:06
 */
public interface ICartService {
    Long addCart(Integer productId, Integer num, String cartId);

    List<CartBean> queryCartList(String cartId);

    Long updateCart(Integer productId, Integer num, String cartId, Integer flag);

    Long checkedAllProduct(String cartId, Boolean checkAllFlag);

    Long deleteProduct(Integer productId, String cartId);

    void delAllCarts(LoginUserInfo userInfo);
}
