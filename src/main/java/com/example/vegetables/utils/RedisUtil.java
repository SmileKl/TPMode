package com.example.vegetables.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lifeixiang
 * @description redis工具类
 * @date 2021-12-28
 */
@Slf4j
public class RedisUtil {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private RedisTemplate redisTemplate;

    protected RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getValue(String key) {
        Object result = redisTemplate.opsForValue().get(key);
        return result == null ? "" : String.valueOf(result);
    }

    /**
     * 默认三秒的API
     * @param userId 放重复提交
     * @param requestMapping 接口标识
     */
    public boolean repeatCommit(Long userId, String requestMapping) {
        return this.repeatCommit(userId, requestMapping, 1);
    }

    /**
     * 校验用户接口操作是否是重复提交
     * @param userId         restContext封装的userId
     * @param requestMapping 接口标识
     * @param second         超时时间
     * @return true second秒内 有该操作 false 非重复提交
     */
    public boolean repeatCommit(Long userId, String requestMapping, long second) {

        final String flag = "repeat";

        if (userId == null || StringUtils.isEmpty(requestMapping) || second <= 0) {
            throw new IllegalArgumentException("userId或requestMapping标识,超时时间(秒)不能小于或等于0");
        }
        final String key = userId + requestMapping;
        //先取值
        String value = getValue(key);
        //存入操作记录
        setValue(key, flag, second);

        return !StringUtils.isEmpty(value);
    }

    public boolean repeatCommit(String Key, String requestMapping, long second) {

        final String flag = "repeat";
        if (StringUtils.isEmpty(Key) || StringUtils.isEmpty(requestMapping) || second <= 0) {
            throw new IllegalArgumentException("key或requestMapping标识,超时时间(秒)不能小于或等于0");
        }
        final String key = Key + requestMapping;
        //先取值
        String value = getValue(key);
        //存入操作记录
        setValue(key, flag, second);

        return !StringUtils.isEmpty(value);
    }

    /**
     * 如果为空就set值，并返回true
     * 如果存在(不为空)不进行操作，并返回false
     */
    public boolean setIfAbsent(String key, String v, long duration) {
        if (duration <= 0 || StringUtils.isEmpty(key) || v == null) {
            throw new IllegalArgumentException("key value 不能为空，或超时时间(秒)不能小于等于0");
        }
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, v, duration, TimeUnit.SECONDS));
    }

    /**
     * 尝试获取锁并设置有效时间, 适用于 防止重复提交，锁定资源。
     */
    public Boolean acquireLock(final String lock, final long expired) {
        String value = System.currentTimeMillis() + expired * 1000 + "1";
        return redisTemplate.opsForValue().setIfAbsent(lock, value, expired, TimeUnit.SECONDS);
    }

    public boolean setIfAbsent(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    public boolean tryLock(String key) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "1",3, TimeUnit.SECONDS));
    }
    public boolean tryLock(String key,int expire) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "1",expire, TimeUnit.SECONDS));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void setValue(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key, value, expire);
    }

    public void setValue(String key, String value, long time,TimeUnit expire) {
        redisTemplate.opsForValue().set(key, value,time, expire);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void hsetValue(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void hsetValue(String key, String hashKey, String value, Integer expire) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public void hsetMap(String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void hsetMap(String key, Map map, Integer expire) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public String hgetValue(String key, String hKey) {
        Object result = redisTemplate.opsForHash().get(key, hKey);
        return result == null ? "" : String.valueOf(result);
    }

    public Map hgteAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean zadd(String key, String value, long score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<String> zrangeByScore(String key, long min, long max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Long removeRangeByScore(String key, long min, long max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key,min, max);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值
     *
     * @param key
     * @param newValue
     * @return
     */
    public String getSet(String key, String newValue) {
        return redisTemplate.opsForValue().getAndSet(key, newValue).toString();
    }

    /**
     * 自增
     */
    public Long increment(String key, Long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

}