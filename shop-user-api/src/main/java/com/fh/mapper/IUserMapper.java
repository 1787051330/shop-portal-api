package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.ShopUserBeanPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName IUserMapper
 * @date 2020/6/28 17:38
 */
@Mapper
@Repository
public interface IUserMapper extends BaseMapper<ShopUserBeanPo> {
}
