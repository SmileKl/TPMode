package com.example.vegetables.sharding;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import java.util.*;

/**
 * 日期分表策略
 */
@Slf4j
public class DateShardingAlgorithm extends ShardingAlgorithmTool<String> {
    public DateShardingAlgorithm() {
    }
    //    @Override
//    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
//        return null;
//    }


    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        List<String> tableNameList = new ArrayList<>();
        Map shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        Map rangeValuesMap = complexKeysShardingValue.getColumnNameAndRangeValuesMap();
        //指定
        if (shardingValuesMap.size() > 0 && rangeValuesMap.size() == 0) {
            //必须传corp_id
            if (!shardingValuesMap.containsKey("corp_id")) {
                log.info("分表必须传入corp_id");
                return tableNameList;
            }
            List<String> corpIdList = (List<String>) shardingValuesMap.get("corp_id");
            if (shardingValuesMap.containsKey("bill_start_time")) {
                List<Date> date = (List<Date>) shardingValuesMap.get("bill_start_time");
                String table = shardingTablesCheckAndCreatAndReturn(complexKeysShardingValue.getLogicTableName(),
                        complexKeysShardingValue.getLogicTableName() + "_" + String.valueOf(corpIdList.get(0)) + DateUtil.format(date.get(0), "_yyyy"));
                tableNameList.add(table);
            } else {
                List<String> tables = shardingTablesCheckAndGet(complexKeysShardingValue.getLogicTableName(),
                        complexKeysShardingValue.getLogicTableName() + "_" + String.valueOf(corpIdList.get(0)));
                if (CollectionUtil.isNotEmpty(tables)) {
                    tableNameList.addAll(tables);
                }
            }
        }
        //范围
        if (rangeValuesMap.size() > 0 && shardingValuesMap.size() > 0) {
            Range<Date> valueRange = (Range<Date>) rangeValuesMap.get("bill_start_time");
            Date lowerDate = valueRange.lowerEndpoint();
            Date upperDate = valueRange.upperEndpoint();
            List<String> corpIdList = (List<String>) shardingValuesMap.get("corp_id");
            for (DateTime dateTime : DateUtil.rangeToList(DateUtil.beginOfYear(lowerDate), DateUtil.endOfYear(upperDate), DateField.YEAR)) {
                String resultTableName = complexKeysShardingValue.getLogicTableName() + "_" + String.valueOf(corpIdList.get(0)) + DateUtil.format(dateTime, "_yyyy");
                if (shardingTablesExistsCheck(resultTableName)) {
                    tableNameList.add(resultTableName);
                }
            }
        }
        System.out.println(JSON.toJSONString(tableNameList));
        return tableNameList;
    }

    /**
     * 获取 指定分表
     */
//    @Override
//    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue preciseShardingValue) {
//
//
//        return shardingTablesCheckAndCreatAndReturn(preciseShardingValue.getLogicTableName(),
//                preciseShardingValue.getLogicTableName() + DateUtil.format(preciseShardingValue.getValue(), "_yyyy"));
//    }
//
//    /**
//     * 获取 范围分表
//     */
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue rangeShardingValue) {
//        Range<Date> valueRange = rangeShardingValue.getValueRange();
//        Date lowerDate = valueRange.lowerEndpoint();
//        Date upperDate = valueRange.upperEndpoint();
//        List<String> tableNameList = new ArrayList<>();
//        for (DateTime dateTime : DateUtil.rangeToList(DateUtil.beginOfYear(lowerDate), DateUtil.endOfYear(upperDate), DateField.YEAR)) {
//            String resultTableName = rangeShardingValue.getLogicTableName() + DateUtil.format(dateTime, "_yyyy");
//            if (shardingTablesExistsCheck(resultTableName)) {
//                tableNameList.add(resultTableName);
//            }
//        }
//        return tableNameList;
//    }


//
//    @Override
//    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
//        return null;
//    }
//
//    @Override
//    public Collection<String> doSharding(Collection collection, RangeShardingValue rangeShardingValue) {
//        return null;
//    }
}

