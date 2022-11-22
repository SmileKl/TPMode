package com.example.vegetables.sharding;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 日期分表策略
 */
@Slf4j
public class ComplexShardingAlgorithm extends ShardingAlgorithmTool<String> {

    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        List<String> tableNameList = new ArrayList<>();
        Map shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        //指定(新增修改数据必须要传projectId->需要找指定表)
        if (shardingValuesMap.size() > 0) {
            //必须传project_id
            if (!shardingValuesMap.containsKey("project_id")) {
                log.info("分表必须传入project_id");
                tableNameList.add(complexKeysShardingValue.getLogicTableName());
                return tableNameList;
            }
            List<String> projectIdList = (List<String>) shardingValuesMap.get("project_id");
            String tables = shardingTablesCheckAndCreatAndReturn(complexKeysShardingValue.getLogicTableName(),
                    complexKeysShardingValue.getLogicTableName() + "_" + String.valueOf(projectIdList.get(0)));
            if (StringUtils.isNotEmpty(tables)) {
                tableNameList.add(tables);
            }
        } else {
            //未指定（用于查询，不传projectId可以查询多项目的数据）
            List<String> tablesAnswer = new ArrayList<>();
            List<String> tablesAnswerOption = new ArrayList<>();
            if (complexKeysShardingValue.getLogicTableName().equals("area")) {
                tablesAnswer = shardingTablesCheckAndGet(complexKeysShardingValue.getLogicTableName(),
                        complexKeysShardingValue.getLogicTableName() + "_" + "area");
            }
            if (complexKeysShardingValue.getLogicTableName().equals("category")) {
                tablesAnswerOption = shardingTablesCheckAndGet(complexKeysShardingValue.getLogicTableName(),
                        complexKeysShardingValue.getLogicTableName() + "_" + "category");
            }
            if (CollectionUtil.isNotEmpty(tablesAnswer)) {
                tableNameList.addAll(tablesAnswer);
            }
            if (CollectionUtil.isNotEmpty(tablesAnswerOption)) {
                tableNameList.addAll(tablesAnswerOption);
            }
        }
        if (CollectionUtil.isEmpty(tableNameList)) {
            tableNameList.add(complexKeysShardingValue.getLogicTableName());
        }
        return tableNameList;
    }

}

