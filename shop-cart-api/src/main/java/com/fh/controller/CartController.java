package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.bo.LoginUserInfo;
import com.fh.service.ICartService;
import com.fh.utils.ResponseServer;
import com.fh.vo.CartBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiezhuang
 * @ClassName CartController
 * @date 2020/7/5 13:05
 */
@RestController
@RequestMapping("carts")
public class CartController {

    @Resource
    private ICartService cartService;


    /**
     * 添加商品到购物车(存入redis)
     * @param productId
     * @param num
     * @param request
     * @return
     */
    @PostMapping
    @LoginAnnotation
    public ResponseServer addCart(@RequestParam Integer productId, @RequestParam Integer num, HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Long count = cartService.addCart(productId,num,user.getCartId());
        return ResponseServer.success(count);

    }

    /**
     * 查询出用户购物车所拥有的商品数据
     * @param request
     * @return
     */
    @GetMapping
    @LoginAnnotation
    public ResponseServer queryCartList(HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        List<CartBean> carList = cartService.queryCartList(user.getCartId());
        return ResponseServer.success(carList);
    }

    /**
     * 修改金额数量
     * @param productId
     * @param num
     * @param flag
     * @param request
     * @return
     */
    @PutMapping
    @LoginAnnotation
    public ResponseServer updateCart(@RequestParam Integer productId, @RequestParam Integer num,@RequestParam Integer flag, HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Long count = cartService.updateCart(productId,num,user.getCartId(),flag);
        return ResponseServer.success(count);
    }

    /**
     * 保存单选按钮全选反选的状态
     * @param checkAllFlag
     * @param request
     * @return
     */
    @PutMapping("/{checkedAll}")
    @LoginAnnotation
    public ResponseServer checkedAllProduct(@RequestParam Boolean checkAllFlag, HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Long count = cartService.checkedAllProduct(user.getCartId(),checkAllFlag);
        return ResponseServer.success(count);
    }

    /**
     * 删除购物车单个商品
     * @param productId
     * @param request
     * @return
     */
    @DeleteMapping
    @LoginAnnotation
    public ResponseServer checkedAllProduct(@RequestParam Integer productId, HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Long count = cartService.deleteProduct(productId,user.getCartId());
        return ResponseServer.success(count);
    }

    /**
     * 保存复选框的状态并判断
     * @param request
     * @return
     */
    @GetMapping("/checkedCarts")
    @LoginAnnotation
    public ResponseServer checkedCartLists(HttpServletRequest request){
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        List<CartBean> cartBeans = cartService.queryCartList(user.getCartId());
        List<CartBean> collectList = cartBeans.stream().filter(cartBean -> cartBean.isChecked()).collect(Collectors.toList());
        return ResponseServer.success(collectList);
    }

    /**
     * 清空当前用户的购物车
     * @param request
     * @return
     */
    @DeleteMapping("/delAllCarts")
    @LoginAnnotation
    public ResponseServer delAllCarts(HttpServletRequest request){
        //删除当前用户购物车的所有商品
        //获取用户信息
        LoginUserInfo userInfo = (LoginUserInfo) request.getAttribute("user");
        cartService.delAllCarts(userInfo);
        return ResponseServer.success();
    }

}
