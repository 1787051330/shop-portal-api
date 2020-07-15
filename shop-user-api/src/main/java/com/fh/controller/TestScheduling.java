package com.fh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiezhuang
 * @ClassName TestScheduling
 * @date 2020/7/12 9:06
 */
@Component
@Slf4j
public class TestScheduling {

    @Scheduled(cron="0 0/1 * * * ?")
    public void statusCheck(){
        log.info("每分钟执行一次。开始......");
        log.info("每分钟执行一次。结束。");
    }

    @Scheduled(fixedRate = 20000)
    public void testTasks(){
        log.info("每20执行一次，开始......");
        log.info("每20执行一次，结束");
    }

    @Scheduled(cron = "0 0 10,14,16 * * ?")
    public void testDate(){
        Date date = new Date();
        log.info("触发时间："+date);
        log.info("触发每天上午十点、下午两点、下午四点定时器");
    }


}
