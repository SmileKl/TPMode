package com.example.vegetables.sharding;


import com.example.vegetables.dao.CommonMapper;
import com.example.vegetables.dao.CreateTableSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 分表工具
 */
@Slf4j
public abstract class ShardingAlgorithmTool<T extends Comparable<?>> implements ComplexKeysShardingAlgorithm<T> {

    private static CommonMapper commonMapper;
    private static ShardingUtils shardingUtils;

    private static final HashSet<String> tableNameCache = new HashSet<>();

    /**
     * 手动注入
     */
    public static void setCommonMapper(CommonMapper commonMapper, ShardingUtils shardingUtils) {
        ShardingAlgorithmTool.commonMapper = commonMapper;
        ShardingAlgorithmTool.shardingUtils = shardingUtils;
    }

    /**
     * 判断 分表获取的表名是否存在 不存在则自动建表
     *
     * @param logicTableName  逻辑表名(表头)
     * @param resultTableName 真实表名
     * @return 确认存在于数据库中的真实表名
     */
    public String shardingTablesCheckAndCreatAndReturn(String logicTableName, String resultTableName) {

        synchronized (logicTableName.intern()) {
            // 缓存中有此表 返回
            if (shardingTablesExistsCheck(resultTableName)) {
                return resultTableName;
            }

            // 缓存中无此表 建表 并添加缓存
            CreateTableSql createTableSql = commonMapper.selectTableCreateSql(logicTableName);
            String sql = createTableSql.getCreateTable();
            sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
            sql = sql.replace(logicTableName, resultTableName);
            commonMapper.executeSql(sql);
            tableNameCache.add(resultTableName);
            try {
                //刷新sharding配置
                List<String> list = new ArrayList<>();
                list.add(resultTableName);
                shardingUtils.DtTools(list,null);
            } catch (Exception e) {
                System.out.println("更新表失败>>>>" + e.getMessage());
            }
        }

        return resultTableName;
    }

    //用于查询只传入单个corpId的情况
    public List<String> shardingTablesCheckAndGet(String logicTableName, String resultTableName) {
        List<String> tableNameList = new ArrayList<>();
        for (String tableName : tableNameCache) {
            if (tableName.contains(resultTableName)) {
                tableNameList.add(tableName);
            }
        }
        return tableNameList;
    }

    /**
     * 判断表是否存在于缓存中
     *
     * @param resultTableName 表名
     * @return 是否存在于缓存中
     */
    public boolean shardingTablesExistsCheck(String resultTableName) {
        return tableNameCache.contains(resultTableName);
    }

    /**
     * 缓存重载方法
     *
     * @param schemaName 待加载表名所属数据库名
     * @return
     */
    public static List<String> tableNameCacheReload(String schemaName) {
        // 读取数据库中所有表名
        List<String> tableNameList = commonMapper.getAllTableNameBySchema(schemaName);
        // 删除旧的缓存(如果存在)
        ShardingAlgorithmTool.tableNameCache.clear();
        // 写入新的缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableNameList);
        return tableNameList;
    }

}