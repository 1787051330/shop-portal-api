package com.fh.service;

import com.fh.bo.LoginUserInfo;
import com.fh.utils.ResponseServer;

/**
 * @author xiezhuang
 * @ClassName IPayService
 * @date 2020/7/7 12:32
 */
public interface IPayService {
    ResponseServer createQy(LoginUserInfo userInfo, String outTradeNo);

    ResponseServer checkOrderPayStatus(String outTradeNo, LoginUserInfo userInfo);
}
