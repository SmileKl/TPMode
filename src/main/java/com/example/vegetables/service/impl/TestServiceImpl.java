package com.example.vegetables.service.impl;

import com.example.vegetables.model.Test;
import com.example.vegetables.dao.TestMapper;
import com.example.vegetables.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 朱归华
 * @since 2022-05-06
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
    private final ThreadPoolExecutor commonThreadPoolExecutor;


    public TestServiceImpl(ThreadPoolExecutor commonThreadPoolExecutor) {
        this.commonThreadPoolExecutor = commonThreadPoolExecutor;
    }
}
