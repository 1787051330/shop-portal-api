package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.bo.LoginUserInfo;
import com.fh.mapper.IAddressMapper;
import com.fh.po.AddressBeanPo;
import com.fh.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName AddressServiceImpl
 * @date 2020/7/6 13:41
 */
@Service("addressService")
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressMapper addressMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String ADDRESS_KEY="address_";

    @Override
    public List<AddressBeanPo> queryAddressUser(Integer userId) {
        //从缓存中取出地址信息
        List<AddressBeanPo> addressList = (List<AddressBeanPo>) redisTemplate.opsForValue().get(ADDRESS_KEY+userId);
        //判断缓存中是否有值没有的话从数据库查询放到缓存中
        if(CollectionUtils.isEmpty(addressList)){
            addressList = addressMapper.selectList(new QueryWrapper<AddressBeanPo>().eq("creatorId",userId));
            redisTemplate.opsForValue().set(ADDRESS_KEY + userId,addressList);
        }
        return addressList;
    }

    @Override
    public void addRess(String consignee, String address, String phone, String email, String alias, Integer userId) {
        AddressBeanPo addressBeanPo = new AddressBeanPo();
        addressBeanPo.setConsignee(consignee);
        addressBeanPo.setAddress(address);
        addressBeanPo.setPhone(phone);
        addressBeanPo.setEmail(email);
        addressBeanPo.setAlias(alias);
        addressBeanPo.setCreatorId(userId);
        addressBeanPo.setCreateTime(new Date());
        addressMapper.insert(addressBeanPo);
        //执行完添加方法之后删除所有的key重新从数据库查询数据并且重新放到redis中去
        redisTemplate.delete(ADDRESS_KEY+userId);
        List<AddressBeanPo> addressList  =  addressMapper.selectList(new QueryWrapper<AddressBeanPo>().eq("creatorId",userId));
        redisTemplate.opsForValue().set(ADDRESS_KEY + userId,addressList);

    }

    @Override
    public void delAddress(Integer addressId,LoginUserInfo userInfo) {
        //删除数据并更新redis
        addressMapper.deleteById(addressId);
        //更新redis中的数据
        redisTemplate.delete(ADDRESS_KEY+userInfo.getUserId());
        List<AddressBeanPo> addressList  =  addressMapper.selectList(new QueryWrapper<AddressBeanPo>().eq("creatorId",userInfo.getUserId()));
        redisTemplate.opsForValue().set(ADDRESS_KEY + userInfo.getUserId(),addressList);
    }

    @Override
    public void addUpdateress(Integer id, String consignee, String address, String phone, String email, String alias, Integer userId) {
        AddressBeanPo addressBeanPo = new AddressBeanPo();
        addressBeanPo.setAddress(address);
        addressBeanPo.setId(id);
        addressBeanPo.setPhone(phone);
        addressBeanPo.setEmail(email);
        addressBeanPo.setAlias(alias);
        addressBeanPo.setConsignee(consignee);
        addressBeanPo.setCreateTime(new Date());
        addressBeanPo.setCreatorId(userId);
        addressMapper.updateById(addressBeanPo);
        //更新redis中的数据
        redisTemplate.delete(ADDRESS_KEY+userId);
        List<AddressBeanPo> addressList  =  addressMapper.selectList(new QueryWrapper<AddressBeanPo>().eq("creatorId",userId));
        redisTemplate.opsForValue().set(ADDRESS_KEY + userId,addressList);
    }

}
