package com.example.vegetables.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动态分表配置
 *
 * @author qimok
 * @since 2020-09-07
 */
@ConfigurationProperties(prefix = "dynamic.table")
@Data
@Component
public class DynamicTablesProperties {
 
    String[] names;
 
}