package com.example.vegetables;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.example.vegetables.dao.TestMapper;
import com.example.vegetables.param.TreeNodes;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class VegetablesApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 128, 30L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    private Integer pits = 500;


    @Test
    void contextLoads() {
        for (int i = 0; i < 10; i++) {
            executor.execute(this::safe);
        }
    }

    /**
     * 手动算费
     */
    @Transactional
    public synchronized void safe() {
        if (pits < 0) {
            System.out.println("票已卖完！");
            return;
        }
        System.out.println("目前票数>>" + pits);
        pits -= 1;
        System.out.println("成功卖出一张票,剩余票数>>" + pits);

    }

    @Test
    void test005() {
        Map<String, Object> map = new HashMap();
        map.put("name", "大帅哥");
        map.put("age", "18");
        stringRedisTemplate.opsForHash().putAll("map", map);
        stringRedisTemplate.opsForHash().entries("map");
        System.out.println(stringRedisTemplate.opsForHash().entries("map"));
        System.out.println(stringRedisTemplate.opsForHash().get("map", "name"));
    }

    @Test
    public void test1() {
        List<TreeNodes> nodeList = new ArrayList<>();
        TreeNodes TreeNode = new TreeNodes();
        TreeNode.setParentId(0L);
        TreeNode.setId(111L);
        TreeNode.setName("父节点");
        TreeNode.setPath("/0/111");
        nodeList.add(TreeNode);

        TreeNodes TreeNode11 = new TreeNodes();
        TreeNode11.setParentId(111L);
        TreeNode11.setId(222L);
        TreeNode11.setName("第二层");
        TreeNode11.setPath("/0/111/222");
        nodeList.add(TreeNode11);
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(3);
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig,
                (TreeNodes, tree) -> {
                    tree.setId(String.valueOf(TreeNode.getId()));
                    tree.setParentId(String.valueOf(TreeNode.getParentId()));
                    tree.setName(TreeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("path", TreeNode.getPath());
                });
        System.out.println(JSON.toJSONString(treeNodes));
    }

    @Test
    public void test22() {
        System.out.println(new Date());
        System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd"));
        System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd") + " 00:00:00");
        System.out.println("结束" + DateUtil.parse((DateUtil.format(new Date(), "yyyy-MM-dd") + " 23:59:59"), "yyyy-MM-dd HH:mm:ss"));
    }

    @Test
    public void test34() {
        List<String> str = new ArrayList<>();
        str.add("D栋");
        str.add("C栋");
        str.add("B栋");
        str.sort(String::compareTo);
        System.out.println(JSON.toJSONString(str));
    }

    @Test
    public void test29() {
        String dateStr1 = "2017-01-01";
        Date date1 = DateUtil.parse(dateStr1);
        String dateStr2 = "2017-10-01 23:59:59";
        Date date2 = DateUtil.parse(dateStr2);
//        System.out.println(DateUtil.compare(date1, new Date()));
//        System.out.println(date1.before(date2));
//        System.out.println(DateUtil.month(date2));
//        System.out.println(DateUtil.month(date1));
//        System.out.println(DateUtil.year(date1));
//        System.out.println(DateUtil.offsetMonth(date1, -1));
//        System.out.println(DateUtil.format(date2,"yyyyMM"));
//        System.out.println(DateUtil.month(date1));
//        System.out.println("开始");
//        System.out.println(DateUtil.beginOfMonth(date2));
//        System.out.println(DateUtil.endOfMonth(date2));
//        System.out.println(DateUtil.month(date2));
//        System.out.println(DateUtil.compare(date2, date1));
//        System.out.println(DateUtil.formatBetween(date1, date2, BetweenFormatter.Level.SECOND));
//        System.out.println(DateUtil.between(date1, date2, DateUnit.DAY));
//        System.out.println(DateUtil.between(new Date(),DateUtil.parse("2022-5-12 20:13:11"),DateUnit.MINUTE));
        List<DateTime> dateTimes = DateUtil.rangeToList(date1, date2, DateField.MONTH);
        dateTimes.forEach(dateTime -> {

        });
    }

    @Test
    public void testRedis() throws InterruptedException {
//        System.out.println("设置过期时间20S");
//        stringRedisTemplate.opsForValue().set("man","manNew",20L,TimeUnit.SECONDS);
//        System.out.println(stringRedisTemplate.opsForValue().getOperations().getExpire("man",TimeUnit.SECONDS));
//        System.out.println();
//
//        System.out.println("修改key值,查看过期时间");
//        stringRedisTemplate.opsForValue().set("man","manNew_xg");
//        System.out.println(stringRedisTemplate.opsForValue().getOperations().getExpire("man",TimeUnit.SECONDS));

//        System.out.println("设置过期时间到今天末");
//        System.out.println(DateUtil.date());
//        stringRedisTemplate.opsForValue().set("women","manNew",DateUtil.between(DateUtil.date(),DateUtil.endOfDay(new Date()), DateUnit.SECOND),TimeUnit.SECONDS);
//        System.out.println(stringRedisTemplate.opsForValue().getOperations().getExpire("women",TimeUnit.SECONDS));
//        System.out.println();
//
//        System.out.println("等待2s");
//        Thread.sleep(2000L);
//        System.out.println("修改key值,查看过期时间");
//        stringRedisTemplate.opsForValue().set("women","manNew_xg",DateUtil.between(DateUtil.date(),DateUtil.endOfDay(new Date()), DateUnit.SECOND),TimeUnit.SECONDS);
//        System.out.println(stringRedisTemplate.opsForValue().getOperations().getExpire("women",TimeUnit.SECONDS));
        System.out.println(DateUtil.format(DateUtil.date(), "yyyyMMdd"));
    }

    @Test
    public void test32() {
        com.example.vegetables.model.Test test = new com.example.vegetables.model.Test();
        String dateStr1 = "2017-05-01";
        test.setTime(DateUtil.parse(dateStr1, "yyyy-MM-dd"));
        testMapper.insert(test);
    }

    @Test
    public void testFloat() {
        BigDecimal bigDecimal = null;
        System.out.println(bigDecimal);
        System.out.println(Float.valueOf(String.valueOf(bigDecimal)));

    }

    @Test
    public void testMath() {
//        System.out.println(NumberUtil.round(1.5, 0, RoundingMode.valueOf(4)));
//        System.out.println(NumberUtil.round(1.5, 0,RoundingMode.UP);
//        System.out.println(NumberUtil.round(1.5, 0,RoundingMode);
        Float a = 70F;
        Integer b = 100;
        BigDecimal round = NumberUtil.round(NumberUtil.div(a, b), 2);
        System.out.println(round);
        System.out.println(NumberUtil.toBigDecimal(round));
//        System.out.println(NumberUtil.round());
    }

    @Test
    public void test09() {
        // 创建一个事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 显式设置事务名称是只能通过编程完成的操作
        def.setName("mani");
        // 设置事务传播行为
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 开始事务
        TransactionStatus status = transactionManager.getTransaction(def);
        com.example.vegetables.model.Test test = new com.example.vegetables.model.Test();
        test.setTime(DateUtil.date());
        testMapper.insert(test);
        System.out.println("返回》》》》》");
        transactionManager.commit(status);
        executor.execute(this::add);

    }

    @Test
    public void add() {
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
            exception();
            transactionManager.commit(status);
        } catch (Exception e) {
            System.out.println("回滚>>>" + e.getMessage());
            transactionManager.rollback(status);
        }
    }

    private void exception() throws Exception {
        throw new Exception("回滚");
    }

    @Test
    void testList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(CollectionUtil.sub(list, 0, 3));
        System.out.println(70 % 100);
    }

    @Test
    void testGS() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("m", Float.valueOf("2.443"));
        context.put("n", Float.valueOf("2"));
        context.put("b", Float.valueOf("2"));
        context.put("d", Float.valueOf("2"));
        context.put("e", Float.valueOf("2"));
        context.put("f", Float.valueOf("2"));
        context.put("g", Float.valueOf("2"));
        String express = "c=((b*(m+n+d-e)/f))*g";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    @Test
    void testGS001() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("m", Float.valueOf("2.443"));
        String express = "c=m";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    @Test
    void testSub() {
        List<String> receivables = new ArrayList<>();
        receivables.add("AAA");
        receivables.add("BBB");
        receivables.add("CCC");
        receivables.add("DDD");
        receivables.add("EEE");
        receivables.add("FFF");
        receivables.add("GGG");
        Integer dataSize = receivables.size();
        Integer maxLength = 2;
        int length = Integer.parseInt(NumberUtil.round(NumberUtil.div(dataSize, maxLength), 0, RoundingMode.UP).toString());
        for (int i = 0; i < length; i++) {
            List<String> sub;
            if (i + 1 == length) {
                sub = CollectionUtil.sub(receivables, i * maxLength, receivables.size());
            } else {
                sub = CollectionUtil.sub(receivables, i * maxLength, (i + 1) * maxLength);
            }
            System.out.println(sub);
        }
    }

    @Test
    void testMap() {
        System.out.println(stringRedisTemplate.opsForValue().get("dds"));
        stringRedisTemplate.opsForValue().set("fdd", "dd");
        System.out.println(stringRedisTemplate.opsForValue().get("fdd"));
    }

    @Test
    void testHuTool() {
        String str = "task_timing_id8892341";
        System.out.println(StrUtil.removePrefix(str, "task_timing_id"));
    }
}
