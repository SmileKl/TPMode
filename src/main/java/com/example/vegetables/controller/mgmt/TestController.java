package com.example.vegetables.controller.mgmt;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.vegetables.config.ShardingConfig;
import com.example.vegetables.dao.TestMapper;
import com.example.vegetables.model.Test;
import com.example.vegetables.service.ITestService;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 朱归华
 * @since 2022-05-06
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ITestService testService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Resource
    private ShardingConfig shardingConfig;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 128, 30L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    private Integer pits = 500;

    @PostMapping("/test001")
    public void test(@RequestBody Test test){
//        testMapper.insert(test);
        List<Test> list = new ArrayList<>();
        for (int i=1;i<10;i++){
            Test test1 = new Test();
            test1.setTime(test.getTime());
            test1.setCreateDate(DateUtil.offsetMonth(test.getTime(),i));
//            test1.setSex(test.getSex());
            list.add(test1);
        }
        testMapper.batchInsertOrUpdate(list);
    }

    @PostMapping("/test002")
    public void test002(@RequestBody Test test){
//        List<Test> list = new ArrayList<>();
//        for (int i=1;i<2;i++){
//            Test test1 = new Test();
//            test1.setTime(DateUtil.offsetMonth(test.getTime(),i));
//            test1.setCreateDate(DateUtil.offsetMonth(test.getTime(),i));
//            test1.setSex(2);
//            list.add(test1);
//        }
        testMapper.updateById(test);
    }

    @GetMapping("/test09")
    @Transactional(rollbackFor = Exception.class)
    public void test09(){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,100L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8));
        executor.execute(this::add);
        Test test = new Test();
//        test.setSex(2);
        testMapper.insert(test);
        System.out.println("返回》》》》》");
    }

    public void add(){
        try {
            Thread.sleep(Long.parseLong("30000"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 创建一个事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 显式设置事务名称是只能通过编程完成的操作
        def.setName("SomeTxName");
        // 设置事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(def);
        Test test = new Test();
        test.setTime(DateUtil.date());
        testMapper.insert(test);
        transactionManager.rollback(status);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/testTrans")
    public void testTrans() {
//        // 创建一个事务
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        // 显式设置事务名称是只能通过编程完成的操作
//        def.setName("mani");
//        // 设置事务传播行为
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        // 开始事务
//        TransactionStatus status = transactionManager.getTransaction(def);
        com.example.vegetables.model.Test test = new com.example.vegetables.model.Test();
        test.setTime(DateUtil.date());
        test.setCreateDate(DateUtil.date());
        testMapper.insert(test);
        System.out.println("返回》》》》》");
//        transactionManager.commit(status);
        executor.execute(this::add111);
    }

    public void add111() {
        // 创建一个事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 显式设置事务名称是只能通过编程完成的操作
        def.setName("SomeTxName");
        // 设置事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(def);
        com.example.vegetables.model.Test test = new com.example.vegetables.model.Test();
//        test.setTime(DateUtil.offsetMonth(DateUtil.date(),1));
        try {
            test.setTime(DateUtil.date());
            testMapper.insert(test);
//            exception();
            transactionManager.commit(status);
        } catch (Exception e) {
            System.out.println("回滚>>>" + e.getMessage());
            transactionManager.rollback(status);
        }
    }

    @PostMapping("/testZi")
    public void testZi() throws Exception {
//        File yml = new File(Objects.requireNonNull(TestController.class.getClassLoader().getResource("application.yml")).toURI());
//        //不管执行什么操作一定要先执行这个
//        YmlUtil.setYmlFile(yml);
//        System.out.println(YmlUtil.getByKey("spring.shardingsphere.sharding.tables.test.actual-data-nodes"));
//        System.out.println("aaaaaa");

        LambdaQueryWrapper<Test> lqw = new LambdaQueryWrapper<>();
//        lqw.le(com.example.vegetables.model.Test::getTime, DateUtil.parse("2022-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//        lqw. System.out.println(testService.list());ge(com.example.vegetables.model.Test::getTime, DateUtil.parse("2020-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(testService.list());

    }

    @PostMapping("/addTest")
    public void addTest(@RequestBody Test test){
        testService.save(test);
    }

    @PostMapping("/getConfig")
    public void getConfig(){
        System.out.println(">>>>>>>>::"+shardingConfig.getActualDataNodes());
    }

}
