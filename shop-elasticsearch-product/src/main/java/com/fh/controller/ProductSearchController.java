package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.bean.PageBean;
import com.fh.bean.SearchBean;
import com.fh.bo.LoginUserInfo;
import com.fh.service.IProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezhuang
 * @ClassName ProductSearchController
 * @date 2020/7/2 12:02
 */
@RestController
@RequestMapping("searchs")
public class ProductSearchController {

    @Autowired
    private IProductSearchService productSearchService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;
    private static final String CART_KEY="cart_";

    @PostMapping
    @LoginAnnotation
    public Map<String,Object> queryProductSearchList(PageBean pageBean, String name){
        Map<String,Object> map = new HashMap<>();
        PageBean<SearchBean> page = productSearchService.queryProductSearchList(pageBean,name);
        LoginUserInfo user = (LoginUserInfo) request.getAttribute("user");
        Long size = redisTemplate.opsForHash().size(CART_KEY + user.getCartId());
        map.put("data",page);
        map.put("code",200);
        map.put("size",size);
        return map;
    }

}
