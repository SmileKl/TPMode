package com.example.vegetables.utils;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lifeixiang
 * @description 构建RedisTemplate
 * @date 2021-12-28
 */
public class RedisUtilsBuilder {

    public static RedisUtil build(String hostname, Integer port, String password) {

        RedisStandaloneConfiguration standConfig = new RedisStandaloneConfiguration();

        standConfig.setPort(port);

        standConfig.setPassword(password);

        standConfig.setHostName(hostname);

        JedisConnectionFactory fac = new JedisConnectionFactory(standConfig);
        fac.afterPropertiesSet();
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(fac);

        template.setDefaultSerializer(new StringRedisSerializer());


        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        /*hash字符串序列化方法*/
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return new RedisUtil(template);
    }

}
