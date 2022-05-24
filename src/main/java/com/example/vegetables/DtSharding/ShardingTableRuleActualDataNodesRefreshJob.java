//package com.example.vegetables.DtSharding;
//
//
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.core.rule.DataNode;
//import org.apache.shardingsphere.core.rule.ShardingRule;
//import org.apache.shardingsphere.core.rule.TableRule;
//import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * 基于范围分表的 ActualDataNodes 动态刷新JOB
// *
// * @author qimok
// * @since 2020-09-07
// */
//@Slf4j
//@Component
//@EnableScheduling
//@Order
//public class ShardingTableRuleActualDataNodesRefreshJob implements InitializingBean {
//
//
//
//    @Resource
//    private DataSource dataSource;
//
//
//
//    private static final Set<String> tableSet = new HashSet<>();
//
//    @Scheduled(cron = "0/1 * * * * ?")
//    //刷新节点的逻辑,核心通过反射更新tableRule,
//    public void actualTablesRefresh() throws Exception {
//        log.info("-----开始刷新表节点-----");
//        ShardingDataSource shardingDataSource = (ShardingDataSource)dataSource;
//        //运行时获取分片规则
////        ShardingRule rule = shardingDataSource.getShardingContext().getShardingRule();
//        ShardingRule rule = null;
//
//        //获取分表策略集合
//        Collection<TableRule> tableRules = rule.getTableRules();
//
//        for (TableRule tableRule : tableRules) {
//            //获取真实节点
//            List<DataNode> actualDataNodes = tableRule.getActualDataNodes();
//
//            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
//
//            //数据源名
//            String dataSourceName = actualDataNodes.get(0).getDataSourceName();
//            //逻辑表名
//            String logicTableName = tableRule.getLogicTable();
//            //根据真实业务,新增节点的逻辑
//            String tableName = actualDataNodes.get(actualDataNodes.size() - 1).getTableName();
//
//            //新增节点
//            actualDataNodes.add(new DataNode(dataSourceName+"."+logicTableName+"_"+"1"));
//
//            actualDataNodesField.setAccessible(true);
//            actualDataNodesField.set(tableRule, actualDataNodes);
//
//            Set<String> actualTables = Sets.newHashSet();
//            Map<DataNode, Integer> dataNodeIntegerMap = Maps.newHashMap();
//            //更新actualTables、dataNodeIntegerMap
//            AtomicInteger a = new AtomicInteger(0);
//            actualDataNodes.forEach((dataNode -> {
//                actualTables.add(dataNode.getTableName());
//                if (a.intValue() == 0){
//                    a.incrementAndGet();
//                    dataNodeIntegerMap.put(dataNode, 0);
//                }else {
//                    dataNodeIntegerMap.put(dataNode, a.intValue());
//                    a.incrementAndGet();
//                }
//            }));
//
//            //动态刷新：actualTables
//            Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
//            actualTablesField.setAccessible(true);
//            actualTablesField.set(tableRule, actualTables);
//            //动态刷新：dataNodeIndexMap
//            Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
//            dataNodeIndexMapField.setAccessible(true);
//            dataNodeIndexMapField.set(tableRule, dataNodeIntegerMap);
////            //动态刷新：datasourceToTablesMap
////            Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
////            datasourceToTablesMap.put(dataSourceName, actualTables);
////            Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
////            datasourceToTablesMapField.setAccessible(true);
////            datasourceToTablesMapField.set(tableRule, datasourceToTablesMap);
//            log.info("-----------------end----------------");
//
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception{
//        actualTablesRefresh();
//    }
//
//}
