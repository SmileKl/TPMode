package com.example.vegetables.sharding;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ShardingUtils {

    @Resource
    private DataSource dataSource;

    public void DtTools(List<String> tableNameList, List<String> removeList) throws Exception {
        log.info("表集合>>" + JSON.toJSONString(tableNameList));
        log.info("-----开始刷新表节点-----");
        ShardingDataSource shardingDataSource = (ShardingDataSource) dataSource;
        //运行时获取分片规则
//        ShardingRule rule = shardingDataSource.getShardingContext().getShardingRule();
        ShardingRule rule = shardingDataSource.getRuntimeContext().getRule();
        //获取分表策略集合
        Collection<TableRule> tableRules = rule.getTableRules();
        for (TableRule tableRule : tableRules) {
            //获取真实节点
            List<DataNode> actualDataNodes = tableRule.getActualDataNodes();
            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
            //数据源名
            String dataSourceName = actualDataNodes.get(0).getDataSourceName();
            //逻辑表名
            String logicTableName = tableRule.getLogicTable();
            //根据真实业务,新增、删除节点的逻辑
//            String tableName = actualDataNodes.get(actualDataNodes.size() - 1).getTableName();
            Map<String, String> tableMap = actualDataNodes.stream().collect(Collectors.toMap(DataNode::getTableName, DataNode::getTableName));
            tableNameList.forEach(s -> {
                if (s.contains(logicTableName + "_") && !tableMap.containsKey(s)) {
                    //排除其他主表
                    String suffix = s.replaceAll(logicTableName + "_", "");
                    if (NumberUtil.isNumber(suffix)){
                        //新增节点
                        actualDataNodes.add(new DataNode(dataSourceName + "." + s));
                    }
                }
            });
            if (CollectionUtils.isNotEmpty(removeList)) {
                Map<String, String> removeTableMap = removeList.stream().collect(Collectors.toMap(s -> s, s -> s));
                actualDataNodes.removeIf(s -> removeTableMap.containsKey(s.getTableName()));
            }
            actualDataNodesField.setAccessible(true);
            actualDataNodesField.set(tableRule, actualDataNodes);
            Set<String> actualTables = Sets.newHashSet();
            Map<DataNode, Integer> dataNodeIntegerMap = Maps.newHashMap();
            AtomicInteger a = new AtomicInteger(0);
            actualDataNodes.forEach((dataNode -> {
                actualTables.add(dataNode.getTableName());
                if (a.intValue() == 0) {
                    a.incrementAndGet();
                    dataNodeIntegerMap.put(dataNode, 0);
                } else {
                    dataNodeIntegerMap.put(dataNode, a.intValue());
                    a.incrementAndGet();
                }
            }));
            //动态刷新：actualTables
            Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
            actualTablesField.setAccessible(true);
            actualTablesField.set(tableRule, actualTables);
            //动态刷新：dataNodeIndexMap
            Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
            dataNodeIndexMapField.setAccessible(true);
            dataNodeIndexMapField.set(tableRule, dataNodeIntegerMap);
            //动态刷新：datasourceToTablesMap
            Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
            datasourceToTablesMap.put(dataSourceName, actualTables);
            Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
            datasourceToTablesMapField.setAccessible(true);
            datasourceToTablesMapField.set(tableRule, datasourceToTablesMap);
            log.info("-----刷新结束-----");
        }
    }

    public static void main(String[] args) {
        String a = "area_project";
        String area_ = a.replaceAll("area_", "");
        System.out.println(NumberUtil.isNumber(area_));
    }
}
