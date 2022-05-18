package com.example.vegetables.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class DelayTaskController {

    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolExecutor commonThreadPoolExecutor;

    public DelayTaskController(StringRedisTemplate redisTemplate, ThreadPoolExecutor commonThreadPoolExecutor) {
        this.redisTemplate = redisTemplate;
        this.commonThreadPoolExecutor = commonThreadPoolExecutor;
    }

    @GetMapping("/delayTest")
    public void delayTest(){
        redisTemplate.opsForValue().set("result","1",5L, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("ssss","3",4L, TimeUnit.SECONDS);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }



}
