package com.fh.service.impl;

import com.fh.mapper.ProductMapper;
import com.fh.mapper.ProductMapperVo;
import com.fh.po.ProductBeanPo;
import com.fh.service.IProductService;
import com.fh.vo.ProductBeanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiezhuang
 * @ClassName ProductServiceImpl
 * @date 2020/7/1 20:11
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Resource
    private ProductMapperVo productMapperVo;

    @Override
    public List<ProductBeanPo> queryListProduct() {
        List<ProductBeanPo> productBeanPos = productMapper.selectList(null);
        return productBeanPos;
    }

    @Override
    public ProductBeanVo getProductById(Integer productId) {
        return productMapperVo.selectById(productId);
    }
}
