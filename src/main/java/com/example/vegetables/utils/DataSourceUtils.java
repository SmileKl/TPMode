package com.example.vegetables.utils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class DataSourceUtils {

    /**
     * 创建数据源对象
     */
    public static DataSource createDataSource(final String dataSourceName) {
        DruidDataSource dataSource = new DruidDataSource();
        String jdbcUrl =
                String.format(
                        "jdbc:mysql://124.222.96.147:3306/%s?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
                        dataSourceName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername("vegetables");
        dataSource.setPassword("vegetables0928..");
        // 数据源其它配置（略）
        return dataSource;
    }
}