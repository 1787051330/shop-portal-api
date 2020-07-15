package com.fh.service;

import com.fh.po.ShopUserBeanPo;

/**
 * @author xiezhuang
 * @ClassName IUserService
 * @date 2020/6/28 17:37
 */
public interface IUserService {
    ShopUserBeanPo queryShopUser(String phone);

    ShopUserBeanPo queryUserAdmin(Integer userId);
}
