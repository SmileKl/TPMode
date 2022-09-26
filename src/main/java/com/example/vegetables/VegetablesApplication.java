package com.example.vegetables;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@MapperScan(basePackages = "com.example.vegetables.dao")
@EnableKafka
public class VegetablesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VegetablesApplication.class, args);
    }

}
