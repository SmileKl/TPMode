package com.example.vegetables.config;

import com.example.vegetables.utils.RedisUtil;
import com.example.vegetables.utils.RedisUtilsBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
public class BeanConfig {

    @Value("${spring.redis.host}")
    private String hostname;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisUtil redisUtils() {
        return RedisUtilsBuilder.build(hostname, port, password);
    }

}
