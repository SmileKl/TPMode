package com.example.vegetables.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

@Slf4j
@Component
public class RedisLock {
    // 锁的过期时间
    private static int EXPIRE_TIME = 1800 * 1000;
    // 锁等待时间
    private static int WAIT_TIME = 50 * 1000;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1]," +
            "ARGV[1],'NX','PX',ARGV[2])", String.class);
    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) " +
            "== ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);
    private static final String LOCK_SUCCESS = "OK";


    public String Lock_with_lua(String key, String value, int seconds) {
        String lock = redisTemplate.execute(SCRIPT_LOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(key),
                value, String.valueOf(seconds));
        final boolean locked = LOCK_SUCCESS.equals(lock);
        return locked ? lock : null;
    }


    public boolean release(String key, String value) {
        String releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(key), value);
        return Boolean.parseBoolean(releaseResult);
    }
}
