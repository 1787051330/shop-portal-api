package com.fh;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName ShopUserApiApplication
 * @author xiezhuang
 * @date 2020/6/27 17:49
 */
@SpringCloudApplication
@EnableRedisRepositories
@EnableScheduling
public class ShopUserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopUserApiApplication.class, args);
    }

}
