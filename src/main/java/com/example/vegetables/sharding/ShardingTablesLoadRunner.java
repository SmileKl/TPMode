package com.example.vegetables.sharding;

import com.example.vegetables.config.ShardingConfig;
import com.example.vegetables.dao.CommonMapper;
import com.example.vegetables.utils.CommonUtils;
import com.example.vegetables.utils.YmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 项目启动后 读取已有分表 进行缓存
 */
@Slf4j
@Order(value = 1) // 数字越小 越先执行
@Component
@EnableScheduling
public class ShardingTablesLoadRunner implements CommandLineRunner {

    @Value("${db.schema-name}")
    private String schemaName;

    @Resource
    private CommonMapper commonMapper;

    @Resource
    private CommonUtils commonUtils;

    @Resource
    private ShardingConfig shardingConfig;

    @Override
    public void run(String... args) throws Exception {

        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper, commonUtils);
        // 调用缓存重载方法
        List<String> tableNameList = ShardingAlgorithmTool.tableNameCacheReload(schemaName);
        commonUtils.DtTools(tableNameList);
        log.info("ShardingTablesLoadRunner start OK");
    }

//    @Scheduled(cron = "0 */1 * * * ?")
//    public void refresh() throws Exception {
//        log.info("定时任务开始");
//        ShardingAlgorithmTool.setCommonMapper(commonMapper,commonUtils);
//        // 调用缓存重载方法
//        List<String> tableNameList = ShardingAlgorithmTool.tableNameCacheReload(schemaName);
//        commonUtils.DtTools(tableNameList);
//        log.info("定时任务结束");
//    }

//    private void editConfig() throws Exception {
//        File yml = new File(Objects.requireNonNull(ShardingTablesLoadRunner.class.getClassLoader().getResource("application.yml")).toURI());
//        YmlUtil.setYmlFile(yml);
//        System.out.println(YmlUtil.getByKey("spring.shardingsphere.sharding.tables.test.actual-data-nodes"));
//        YmlUtil.saveOrUpdateByKey("spring.shardingsphere.sharding.tables.test.actual-data-nodes", "ds.test,ds.test_2022,ds.test_2026,ds.test_2029");
//        System.out.println(YmlUtil.getByKey("spring.shardingsphere.sharding.tables.test.actual-data-nodes"));
//        getConfig();
//    }
//
//    private void getConfig() {
//        System.out.println("配置>>>" + shardingConfig.getActualDataNodes());
//    }
}

