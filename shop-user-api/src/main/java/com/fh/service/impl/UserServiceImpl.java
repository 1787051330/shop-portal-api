package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.mapper.IUserMapper;
import com.fh.po.ShopUserBeanPo;
import com.fh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xiezhuang
 * @ClassName UserServiceImpl
 * @date 2020/6/28 17:37
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;


    @Override
    public ShopUserBeanPo queryShopUser(String phone) {

        ShopUserBeanPo shopUserBeanPo = userMapper.selectOne(new QueryWrapper<ShopUserBeanPo>().eq("phone",phone));

        if(shopUserBeanPo == null){
            shopUserBeanPo = new ShopUserBeanPo();
            shopUserBeanPo.setUserStarts(1);
            shopUserBeanPo.setPhone(phone);
            shopUserBeanPo.setModifiedTime(LocalDateTime.now());
            shopUserBeanPo.setCartId(IdWorker.getIdStr());
            userMapper.insert(shopUserBeanPo);
        }

        return shopUserBeanPo;
    }

    @Override
    public ShopUserBeanPo queryUserAdmin(Integer userId) {
        return userMapper.selectById(userId);
    }
}
