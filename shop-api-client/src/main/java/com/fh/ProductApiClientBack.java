package com.fh;

import com.fh.utils.ResponseServer;
import org.springframework.stereotype.Component;

/**
 * @author xiezhuang
 * @ClassName ProductApiClientBack
 * @date 2020/7/5 13:49
 */
@Component
public class ProductApiClientBack implements ProductApiClient {
    @Override
    public ResponseServer getProductById(Integer productId) {
        return ResponseServer.error();
    }
}
