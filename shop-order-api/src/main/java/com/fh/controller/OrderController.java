package com.fh.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.annotation.LoginAnnotation;
import com.fh.bo.LoginUserInfo;
import com.fh.service.IOrderService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiezhuang
 * @ClassName OrderController
 * @date 2020/7/6 20:37
 */
@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @LoginAnnotation
    public ResponseServer createOrder(@RequestParam Integer addressId,@RequestParam String orderOnlyFlag, HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        String flag = (String) redisTemplate.opsForValue().get("ORDER_ONLY_"+user.getUserId());
        if(StringUtils.isBlank(orderOnlyFlag) || StringUtils.isBlank(flag)){
            return ResponseServer.error(ServerEnum.ORDER_NOTE_TIMEOUT);
        }
        ResponseServer order = orderService.createOrder(user, addressId);
        //获取唯一标识
        redisTemplate.delete("ORDER_ONLY_"+user.getUserId());
        return order;
    }
    
    @GetMapping
    @LoginAnnotation
    public ResponseServer createOnly(HttpServletRequest request){
        //生成唯一标识
        String flagIdstr = IdWorker.getIdStr();
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Integer userId = user.getUserId();
        redisTemplate.opsForValue().set("ORDER_ONLY_"+userId,flagIdstr);
        return ResponseServer.success(flagIdstr);
    }
    
}
