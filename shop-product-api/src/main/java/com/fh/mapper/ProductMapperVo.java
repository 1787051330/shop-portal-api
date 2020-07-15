package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.vo.ProductBeanVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName ProductMapperVo
 * @date 2020/7/5 14:47
 */
@Mapper
@Repository
public interface ProductMapperVo extends BaseMapper<ProductBeanVo> {
}
