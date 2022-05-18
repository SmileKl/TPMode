package com.example.vegetables.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author zhugh
 * @date 2022/5/12 13:30
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    /**
     * 通用的线程池
     *
     * @return
     * @date 2022/5/12 13:31
     */
    @Bean(name = "commonThreadPoolExecutor")
    public ThreadPoolExecutor commonThreadPoolExecutor() {
        log.info("start commonThreadPoolExecutor");
        return new ThreadPoolExecutor(16, 128, 10L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}