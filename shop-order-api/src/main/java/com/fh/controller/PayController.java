package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.bo.LoginUserInfo;
import com.fh.service.IPayService;
import com.fh.utils.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiezhuang
 * @ClassName PayController
 * @date 2020/7/7 12:32
 */
@RestController
@RequestMapping("pays")
public class PayController {

    @Autowired
    private IPayService payService;

    //微信支付接口
    @PostMapping
    @LoginAnnotation
    public ResponseServer createQy(@RequestParam String outTradeNo, HttpServletRequest request){
        LoginUserInfo userInfo = (LoginUserInfo) request.getAttribute("user");
        return payService.createQy(userInfo,outTradeNo);
    }

    @PutMapping
    @LoginAnnotation
    public ResponseServer checkOrderPayStatus(@RequestParam String outTradeNo, HttpServletRequest request){
        LoginUserInfo userInfo = (LoginUserInfo) request.getAttribute("user");
        return payService.checkOrderPayStatus(outTradeNo,userInfo);
    }

}
