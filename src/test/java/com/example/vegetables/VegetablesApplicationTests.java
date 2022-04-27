package com.example.vegetables;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class VegetablesApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,100L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8));
    private Integer pits = 500;


    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            executor.execute(this::safe);
        }
    }

    public synchronized void safe(){
        if (pits < 0){
            System.out.println("票已卖完！");
            return;
        }
        System.out.println("目前票数>>"+pits);
        pits-=1;
        System.out.println("成功卖出一张票,剩余票数>>"+pits);
    }

    @Test
    void test005(){
        Map<String,Object> map = new HashMap();
        map.put("name","大帅哥");
        map.put("age","18");
        stringRedisTemplate.opsForHash().putAll("map",map);
        stringRedisTemplate.opsForHash().entries("map");
        System.out.println(stringRedisTemplate.opsForHash().entries("map"));
        System.out.println(stringRedisTemplate.opsForHash().get("map", "name"));
    }

}
