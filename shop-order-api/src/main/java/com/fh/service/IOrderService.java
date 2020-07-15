package com.fh.service;

import com.fh.bo.LoginUserInfo;
import com.fh.utils.ResponseServer;

/**
 * @author xiezhuang
 * @ClassName IOrderService
 * @date 2020/7/6 20:42
 */
public interface IOrderService {
    ResponseServer createOrder(LoginUserInfo user, Integer addressId);
}
