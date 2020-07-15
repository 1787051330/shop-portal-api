package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.ProductBeanPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName ILuceneMapper
 * @date 2020/6/30 12:41
 */
@Repository
@Mapper
public interface ProductMapper extends BaseMapper<ProductBeanPo> {
}
