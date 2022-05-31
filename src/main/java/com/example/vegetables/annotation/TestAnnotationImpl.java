package com.example.vegetables.annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 实现注解逻辑
 * @Aspect 定义切面类
 * @Component 注册为容器组件
 */
@Aspect
@Component
public class TestAnnotationImpl {

    //定义切点
    @Pointcut("@annotation(com.example.vegetables.annotation.TestAnnotation)")
    private void cut(){
    }

    @Before("cut()")
    public void before(){
        System.out.println("自定义注解前置通知！");
    }
    @After("cut()")
    public void after(){
        System.out.println("自定义注解后置通知！");
    }
}
