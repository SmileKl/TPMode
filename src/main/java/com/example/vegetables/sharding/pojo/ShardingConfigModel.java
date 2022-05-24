package com.example.vegetables.sharding.pojo;

import lombok.Data;

@Data
public class ShardingConfigModel {
    /**
     * 逻辑表名
     */
    private String logicTableName;
    /**
     * guava 语法写的实际数据表表达式
     */
    private String actualDataNodes;
}