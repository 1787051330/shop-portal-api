package com.fh.config;

import com.fh.service.ILuceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xiezhuang
 * @ClassName ProductRunner
 * @date 2020/6/30 12:27
 */
@Component
@Order(3)
public class ProductRunner implements ApplicationRunner {

    @Autowired
    private ILuceneService luceneService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        luceneService.syncProductCreateindex();
    }
}
