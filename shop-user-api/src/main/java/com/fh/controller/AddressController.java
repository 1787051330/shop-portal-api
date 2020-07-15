package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.bo.LoginUserInfo;
import com.fh.po.AddressBeanPo;
import com.fh.service.IAddressService;
import com.fh.utils.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName AddressController
 * @date 2020/7/6 13:39
 */
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping
    @LoginAnnotation
    public ResponseServer queryAddressUser(HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        List<AddressBeanPo> addressList = addressService.queryAddressUser(user.getUserId());
        return ResponseServer.success(addressList);
    }

    /**
     * 对当前用户添加地址操作
     * @param consignee
     * @param address
     * @param phone
     * @param email
     * @param alias
     * @param request
     * @return
     */

    @PostMapping
    @LoginAnnotation
    public ResponseServer addRess(@RequestParam String consignee,@RequestParam String address,@RequestParam String phone,@RequestParam String email,@RequestParam String alias, HttpServletRequest request){
        //获取当前用户ID添加
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Integer userId = user.getUserId();
        addressService.addRess(consignee,address,phone,email,alias,userId);
        return ResponseServer.success();
    }

    @DeleteMapping
    @LoginAnnotation
    public ResponseServer delAddress(@RequestParam Integer addressId, HttpServletRequest request){
        //删除当前用户的地址信息
        LoginUserInfo userInfo = (LoginUserInfo) request.getAttribute("user");
        addressService.delAddress(addressId,userInfo);
        return ResponseServer.success();
    }

    @PutMapping
    @LoginAnnotation
    public ResponseServer addUpdateress(@RequestParam Integer id, @RequestParam String consignee, @RequestParam String address, @RequestParam String phone, @RequestParam String email, @RequestParam String alias, HttpServletRequest request){
        //System.out.println(addressBeanPo);
        //获取当前用户ID修改
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Integer userId = user.getUserId();
        addressService.addUpdateress(id,consignee,address,phone,email,alias,userId);
        return ResponseServer.success();
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("进入测试接口方法");
        return "测试接口";
    }

}
