package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.PayLogBeanPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName IPayLogMapper
 * @date 2020/7/6 22:16
 */
@Repository
@Mapper
public interface IPayLogMapper extends BaseMapper<PayLogBeanPo> {
}
