package com.example.vegetables.sharding;

import com.alibaba.fastjson.JSON;
import com.example.vegetables.dao.CommonMapper;
import com.example.vegetables.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ShardingUtils shardingUtils;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {

        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper, shardingUtils);
        // 调用缓存重载方法
        List<String> tableNameList = ShardingAlgorithmTool.tableNameCacheReload(schemaName);
        //刷新sharding配置
        shardingUtils.DtTools(tableNameList,null);
        log.info("ShardingTablesLoadRunner start OK");
    }

    /**
     * 定时刷新sharding配置
     *
     * @author zhugh
     * @date 2022/5/23 16:08
     */
//    @XxlJob("property-refreshShardingTables")
    @Scheduled(cron = "0/10 * * * * ?")
    public void refresh() throws Exception {
//        log.info("定时任务开始");
        ShardingAlgorithmTool.setCommonMapper(commonMapper, shardingUtils);
        // 调用缓存重载方法
        List<String> tableNameList = ShardingAlgorithmTool.tableNameCacheReload(schemaName);
        if (StringUtils.isEmpty(redisUtil.getValue("tableNameList"))) {
            redisUtil.setValue("tableNameList", JSON.toJSONString(tableNameList));
            shardingUtils.DtTools(tableNameList,null);
//            log.info("定时任务结束");
            return;
        }
        List<String> stringList = JSON.parseArray(redisUtil.getValue("tableNameList"), String.class);
        if (stringList.size() != tableNameList.size()) {
            log.info("有需要定时刷新的表::>>" + JSON.toJSONString(stringList));
            List<String> needRemoveData = needRemoveData(stringList, tableNameList);
            shardingUtils.DtTools(tableNameList,needRemoveData);
            redisUtil.setValue("tableNameList", JSON.toJSONString(tableNameList));
        }
//        log.info("定时任务结束");
    }

    public List<String> needRemoveData(List<String> stringList,List<String> tableNameList){
        Map<String, String> tableMap = tableNameList.stream().collect(Collectors.toMap(s -> s, s -> s));
        return stringList.stream().filter(tableName -> !tableMap.containsKey(tableName)).collect(Collectors.toList());
    }
}

