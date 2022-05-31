package com.example.vegetables.utils;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


@Slf4j
@Component
public class RedisDistributedLock {
    // 锁的过期时间

    @Resource
    private RedisUtil redisUtil;

    // 不断尝试加锁
    public String lock(String key, Long expireTime, Integer waitTime) {
        try {
            // 超过等待时间，加锁失败
            long waitEnd = System.currentTimeMillis() + waitTime * 1000;
            String value = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < waitEnd) {
                if (redisUtil.setIfAbsent(key, value, expireTime)) {
                    System.out.println("设置key>>" + key);
                    return key;
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception ex) {
            log.error("lock error", ex);
        }
        return null;
    }

    public boolean release(String key) {
        if (key == null) {
            return false;
        }
        redisUtil.delete(key);
        System.out.println(redisUtil.getValue(key));
        return StringUtils.isEmpty(redisUtil.getValue(key));
    }

}