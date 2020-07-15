package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.AddressBeanPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName IAddressMapper
 * @date 2020/7/6 13:41
 */
@Repository
@Mapper
public interface IAddressMapper extends BaseMapper<AddressBeanPo> {
}
