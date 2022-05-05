package com.example.vegetables.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class DelayTaskController {

    private final StringRedisTemplate redisTemplate;

    public DelayTaskController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/delayTest")
    public void delayTest(){
        redisTemplate.opsForValue().set("result","1",5L, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("result"));
    }

    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }



}
