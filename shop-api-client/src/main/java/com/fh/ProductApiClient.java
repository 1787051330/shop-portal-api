package com.fh;

import com.fh.utils.ResponseServer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiezhuang
 * @ClassName ProductApiClient
 * @date 2020/7/5 13:35
 */
@FeignClient(value="shop-product-api",fallbackFactory = ProductApiClientBack.class)
public interface ProductApiClient {

    @GetMapping("/products/{productId}")
    ResponseServer getProductById(@PathVariable Integer productId);

}
