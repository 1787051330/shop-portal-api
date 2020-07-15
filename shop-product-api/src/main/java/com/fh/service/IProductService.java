package com.fh.service;

import com.fh.po.ProductBeanPo;
import com.fh.vo.ProductBeanVo;

import java.util.List;

/**
 * @author xiezhuang
 * @ClassName IProductService
 * @date 2020/7/1 20:11
 */
public interface IProductService {
    List<ProductBeanPo> queryListProduct();

    ProductBeanVo getProductById(Integer productId);
}
