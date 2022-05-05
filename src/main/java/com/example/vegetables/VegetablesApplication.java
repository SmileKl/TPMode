package com.example.vegetables;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.vegetables.dao")
public class VegetablesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VegetablesApplication.class, args);
    }

}
