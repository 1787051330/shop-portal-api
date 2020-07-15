package com.fh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.po.OrderBeanPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author xiezhuang
 * @ClassName IOrderMapper
 * @date 2020/7/6 22:15
 */
@Repository
@Mapper
public interface IOrderMapper extends BaseMapper<OrderBeanPo> {
    @Update("update t_shop_product set stock=stock-#{num} where stock-#{num} >=0 and id=#{productId}")
    int updateProductStock(@Param("num") Integer num, @Param("productId") Integer productId);
}
