package com.example.vegetables.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class DelayTaskController {

    private final StringRedisTemplate redisTemplate;

    public DelayTaskController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/delayTest")
    private void delayTest(){
        redisTemplate.opsForValue().set("result","1",5L, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("result"));
    }



}
