package com.example.vegetables.controller.mgmt;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.vegetables.annotation.TestAnnotation;
import com.example.vegetables.config.ShardingConfig;
import com.example.vegetables.dao.TestMapper;
import com.example.vegetables.model.Test;
import com.example.vegetables.service.ITestService;
import com.example.vegetables.utils.RedisLock;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
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

    @Resource
    private RedisLock redisLock;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 128, 30L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    private Integer pits = 500;

    @PostMapping("/test001")
    public void test(@RequestBody Test test) {
//        testMapper.insert(test);
        List<Test> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Test test1 = new Test();
            test1.setBillStartTime(test.getBillStartTime());
            test1.setCreateDate(DateUtil.offsetMonth(test.getBillStartTime(), i));
//            test1.setSex(test.getSex());
            list.add(test1);
        }
        testMapper.batchInsertOrUpdate(list);
    }

    @PostMapping("/test002")
    public void test002(@RequestBody Test test) {
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
    public void test09() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 100L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8));
        executor.execute(this::add);
        Test test = new Test();
//        test.setSex(2);
        testMapper.insert(test);
        System.out.println("返回》》》》》");
    }

    public void add() {
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
        test.setBillStartTime(DateUtil.date());
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
        test.setBillStartTime(DateUtil.date());
        test.setCreateDate(DateUtil.date());
        testMapper.insert(test);
        System.out.println("返回》》》》》");
//        transactionManager.commit(status);
        executor.execute(this::add111);
    }

    @PostMapping("/testAdd")
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
            test.setBillStartTime(DateUtil.date());
            testMapper.insert(test);
//            exception();
            Thread.sleep(4000);
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
        lqw.le(com.example.vegetables.model.Test::getBillStartTime, DateUtil.parse("2030-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        lqw.ge(com.example.vegetables.model.Test::getBillStartTime, DateUtil.parse("2020-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        lqw.eq(Test::getCorpId, 1420315293208961026L);
        System.out.println(testService.list(lqw));

    }

    @PostMapping("/testZiPP")
    public void testZiPP() throws Exception {
//        File yml = new File(Objects.requireNonNull(TestController.class.getClassLoader().getResource("application.yml")).toURI());
//        //不管执行什么操作一定要先执行这个
//        YmlUtil.setYmlFile(yml);
//        System.out.println(YmlUtil.getByKey("spring.shardingsphere.sharding.tables.test.actual-data-nodes"));
//        System.out.println("aaaaaa");

        LambdaQueryWrapper<Test> lqw = new LambdaQueryWrapper<>();
//        lqw.le(com.example.vegetables.model.Test::getBillStartTime, DateUtil.parse("2030-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//        lqw.ge(com.example.vegetables.model.Test::getBillStartTime, DateUtil.parse("2020-05-30 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//        lqw.eq(Test::getCorpId,1420315293208961026L);
        System.out.println(testService.list(lqw));

    }

    @PostMapping("/testUp")
    public void testUp() {
        LambdaUpdateWrapper<Test> luw = new LambdaUpdateWrapper<>();
        luw.set(Test::getMsg, "sfasf");
        luw.eq(Test::getCorpId, 1420315293208961026L);
        luw.eq(Test::getBillStartTime, DateUtil.date());
        luw.eq(Test::getId, 1537364451680915457L);
        testService.update(luw);
    }

    @PostMapping("/testUpdate")
    public void testUpdate(@RequestBody Test test) {
        LambdaUpdateWrapper<Test> luw = new LambdaUpdateWrapper<>();
//        luw.set(Test::getMsg,"sfasf");
//        luw.eq(Test::getCorpId,1420315293208961026L);
//        luw.eq(Test::getBillStartTime,DateUtil.date());
//        luw.eq(Test::getId,1537364451680915457L);
        luw.set(Test::getMsg, test.getMsg());
        if (Objects.nonNull(test.getCorpId())) {
            luw.eq(Test::getCorpId, test.getCorpId());
        }
        if (Objects.nonNull(test.getBillStartTime())) {
            luw.eq(Test::getBillStartTime, test.getBillStartTime());
        }
        luw.eq(Test::getId, test.getId());
        testService.update(luw);
    }

    @PostMapping("/testLeft")
    public void testLeft(@RequestBody Test test) {
        if ("1".equals(test.getMsg())){
            List<Test> list = testMapper.testRight();
            System.out.println(list);
            System.out.println(JSON.toJSONString(list));
            return;
        }
        List<Test> list = testMapper.testLeft();
        System.out.println(JSON.toJSONString(list));
    }

    @PostMapping("/addTest")
    public void addTest(@RequestBody Test test) {
        testService.save(test);
    }

    @PostMapping("/getConfig")
    public void getConfig() {
        System.out.println(">>>>>>>>::" + shardingConfig.getActualDataNodes());
    }

    //    @Lock4j
    @PostMapping("/redisLock")
    public void redisLock() throws InterruptedException {
//        System.out.println("加锁>>>>>>>>>>>");
//        String value = redisDistributedLock.lock("save_lock", 200L, 1);
//        System.out.println();
//        if (Objects.isNull(value)){
//            System.out.println("加锁失败！");
//            return;
//        }
//        System.out.println("处理中！！");
//        Thread.sleep(3000L);
//        System.out.println("释放锁！！");
//        if (redisDistributedLock.release("save_lock")) {
//            System.out.println("释放成功");
//            return;
//        }
//        System.out.println("释放失败");

        System.out.println("加锁>>>>>>>>>>>");
        String value = UUID.randomUUID().toString();
        redisLock.Lock_with_lua("save_lock", value, 2000);
        System.out.println("1、新来一个！");
        System.out.println("2、处理中！！");
        Thread.sleep(3000L);
        System.out.println("3、处理完成！！");
        if (redisLock.release("save_lock", value)) {
            System.out.println("释放成功");
            return;
        }
        System.out.println("释放失败");
    }

    @PostMapping("/testKafka")
    public void testKafka(@RequestBody Test test) {
        System.out.println("发送信息::" + test.getMsg());
        kafkaTemplate.send("myFirstTopic", test.getMsg());
    }

    @TestAnnotation
    @PostMapping("/annotation")
    public void annotation() {
        System.out.println("我是谁？");
    }

    @TestAnnotation
    @GetMapping("/testExport")
    public void testExport(HttpServletResponse response) throws IOException {
        List<Test> excelList = new ArrayList<>();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Test.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(excelList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 导出Excel(一个sheet)
     *
     * @param response  HttpServletResponse
     * @param list      数据list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的sheet名
     * @param clazz 实体类
     */
    public static <T> void  writeExcel(HttpServletResponse response,List<T> list, String fileName, String sheetName, Class<T> clazz) {
        OutputStream outputStream = getOutputStream(response, fileName);
        ExcelWriter excelWriter = EasyExcel.write(outputStream, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(list, writeSheet);
        excelWriter.finish();
    }


    /**
     * 导出时生成OutputStream
     */
    private static OutputStream getOutputStream(HttpServletResponse response,String fileName) {
        //创建本地文件
        fileName = fileName + ".xlsx";
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/vnd.ms-excel");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
