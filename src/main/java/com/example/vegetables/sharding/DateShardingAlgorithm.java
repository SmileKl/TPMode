package com.example.vegetables.sharding;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 日期分表策略
 */
public class DateShardingAlgorithm extends ShardingAlgorithmTool<Date> {

    /**
     * 获取 指定分表
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
        return shardingTablesCheckAndCreatAndReturn(preciseShardingValue.getLogicTableName(),
                preciseShardingValue.getLogicTableName() + DateUtil.format(preciseShardingValue.getValue(), "_yyyy"));
    }

    /**
     * 获取 范围分表
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerDate = valueRange.lowerEndpoint();
        Date upperDate = valueRange.upperEndpoint();
        List<String> tableNameList = new ArrayList<>();
        for (DateTime dateTime : DateUtil.rangeToList(DateUtil.beginOfYear(lowerDate), DateUtil.endOfYear(upperDate), DateField.YEAR)) {
            String resultTableName = rangeShardingValue.getLogicTableName() + DateUtil.format(dateTime, "_yyyy");
            if (shardingTablesExistsCheck(resultTableName)) {
                tableNameList.add(resultTableName);
            }
        }
        return tableNameList;
    }


}

