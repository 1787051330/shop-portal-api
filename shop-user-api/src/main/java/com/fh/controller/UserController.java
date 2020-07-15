package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.bo.LoginUserInfo;
import com.fh.jwt.JwtUtils;
import com.fh.po.ShopUserBeanPo;
import com.fh.service.IUserService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserController
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
@RestController
@RequestMapping("users")
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Value("${token.headerToken}")
    private String headerToken;

    @Value("${token.securityKey}")
    private String securityKey;

   /* @GetMapping
    public ResponseServer login(String userName, String password){
        log.info("==========登录方法执行=========");
        System.out.println("获取到的用户名为:"+userName+"获取到的密码为:"+password);
        log.info("==========登录方法结束=========");
        return ResponseServer.success(ServerEnum.LOGIN_SUCCESS);
    }*/

    /**
     * 发送验证码
     * @param phone
     * @return
     */
   @GetMapping
    public ResponseServer SendMessage(String phone){
       log.info("===============执行发送短信接口===================");
        if(StringUtils.isBlank(phone)){
            log.info("===============手机号为空==================");
            return ResponseServer.error(ServerEnum.PHONE_ISNULL);
        }
       //MessageCode.queryTest(phone);
       String code = "123456";
       if(code != null){
           redisTemplate.opsForValue().set("code_"+phone,code,2, TimeUnit.MINUTES);
       }else{
           log.info("===============短信生成失败调用接口获取的短信为空===================");
           return ResponseServer.error(ServerEnum.CODE_ERROR);
       }
       log.info("===============短信接口调用结束===================");
       return ResponseServer.success(ServerEnum.CODE_SUCCESS);
   }

    /**
     * 登录方法`
     * @param phone
     * @return
     */
   @PostMapping
    public ResponseServer login(String phone,String code){
       log.info("============执行登录方法================");
        //首先判断手机号和验证码是否为空
       if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
           log.info("============手机号或者验证码为空================");
           return ResponseServer.error(ServerEnum.LOGIN_PHONEORCODE_INNULL);
       }
       //获取验证码进行比对
       log.info("============获取redis中的验证码进行比对================");
       String redisCode = (String) redisTemplate.opsForValue().get("code_" + phone);

       if(!code.equals(redisCode)){
           log.info("============验证码错误================");
           return ResponseServer.error(ServerEnum.LOGIN_CODE_ERROR);
       }
       log.info("============数据库查询或新增================");
       //去数据库中取出对应的数据如果没有则进行添加
        ShopUserBeanPo shopUserBeanPo = userService.queryShopUser(phone);
       log.info("============用户信息存放redis================");
       //将用户信息放入redis中
        redisTemplate.opsForValue().set("user_"+phone,shopUserBeanPo,1L,TimeUnit.HOURS);
       log.info("============生成token值返回================");
        //生成jwt的字符串
        Map<String,Object> map = new HashMap<>();
        map.put("userId",shopUserBeanPo.getShopId());
        map.put("phone",shopUserBeanPo.getPhone());
        map.put("cartId",shopUserBeanPo.getCartId());
        //注册/登录成功之后删除redis中的验证码
       log.info("============删除验证码================");
        redisTemplate.delete("code_"+phone);
        Map<String,Object> resultMap = JwtUtils.createToken(map, securityKey);
       log.info("============登录方法执行结束================");
        return ResponseServer.success(resultMap);
   }


   @GetMapping("/{token}")
   public ResponseServer refreshToken(String token){
       //判断token不为空
       if(StringUtils.isBlank(token)){
           return ResponseServer.error(ServerEnum.TOKEN_ISNULL);
       }
       //验证token值
       ResponseServer responseServer = JwtUtils.resolveToken(token, securityKey);
       int size = 200;
       if(responseServer.getCode() != size){
           return responseServer;
       }
       Claims userInfo = JwtUtils.getUserInfo(token, securityKey);
       Map<String,Object> tokenMap = JwtUtils.createToken(userInfo,securityKey);
       return ResponseServer.success(tokenMap);
   }

    /**
     * 获取当前登录用户的所有信息
     * @param request
     * @return
     */
   @GetMapping("/getUser")
   @LoginAnnotation
   public ResponseServer queryUserAdmin(HttpServletRequest request){
       LoginUserInfo userInfo = (LoginUserInfo) request.getAttribute("user");
       //通过用户ID查询
       Integer userId = userInfo.getUserId();
        ShopUserBeanPo shopUserBeanPo = userService.queryUserAdmin(userId);
       return ResponseServer.success(shopUserBeanPo);
   }



}
