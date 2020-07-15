package com.fh.controller;

import com.fh.annotation.LoginAnnotation;
import com.fh.po.ProductBeanPo;
import com.fh.service.ILuceneService;
import com.fh.service.IProductService;
import com.fh.utils.ResponseServer;
import com.fh.vo.ProductBeanVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiezhuang
 * @ClassName ProductController
 * @date 2020/6/29 12:29
 */
@RestController
@RequestMapping("products")
@Slf4j
public class ProductController {

    @Autowired
    private ILuceneService luceneService;
    @Autowired
    private IProductService productService;

    /*
    lucene查询
    @GetMapping
    @LoginAnnotation
    public ResponseServer test(SearchBean searchBean) throws IOException, ParseException {
        List<ProductBeanPo> list = luceneService.searchProduct(searchBean);
        System.out.println("访问商品服务");
        System.out.println("集合"+list);
        return ResponseServer.success(list);
    }*/

    /**
     * 查询商品列表
     * @return
     */
    @GetMapping
    @LoginAnnotation
    public ResponseServer queryListProduct(){
        log.info("=============查询商品列表=================");
        List<ProductBeanPo> shopList = productService.queryListProduct();
        return ResponseServer.success(shopList);
    }

    /**
     * 根据商品ID查询的接口
     * @param productId
     * @return
     */

    @GetMapping("/{productId}")
    public ResponseServer getProductById(@PathVariable Integer productId){
        ProductBeanVo productBeanVo = productService.getProductById(productId);
        return ResponseServer.success(productBeanVo);
    }

}
