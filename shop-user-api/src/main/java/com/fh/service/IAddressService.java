package com.fh.service;

import com.fh.bo.LoginUserInfo;
import com.fh.po.AddressBeanPo;

import java.util.List;

/**
 * @author xiezhuang
 * @ClassName IAddressService
 * @date 2020/7/6 13:41
 */
public interface IAddressService {
    List<AddressBeanPo> queryAddressUser(Integer userId);

    void addRess(String consignee, String address, String phone, String email, String alias, Integer userId);

    void delAddress(Integer addressId,LoginUserInfo userInfo);

    void addUpdateress(Integer id, String consignee, String address, String phone, String email, String alias, Integer userId);
}
