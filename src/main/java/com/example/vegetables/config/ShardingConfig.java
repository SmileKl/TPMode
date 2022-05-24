package com.example.vegetables.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.shardingsphere.sharding.tables.test")
@Data
@Component
public class ShardingConfig {

    private String actualDataNodes;

}
