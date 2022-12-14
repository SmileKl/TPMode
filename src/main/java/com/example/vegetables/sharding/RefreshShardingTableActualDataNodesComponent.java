package com.example.vegetables.sharding;

import com.example.vegetables.sharding.pojo.ShardingConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.core.config.inline.InlineExpressionParser;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class RefreshShardingTableActualDataNodesComponent  {

    private final ShardingDataSource dataSource;

    public RefreshShardingTableActualDataNodesComponent(ShardingDataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * 刷新表
     */
    public synchronized void refresh(ShardingConfigModel configModel) {
        String logicTableName = configModel.getLogicTableName();
        String actualDataNodes = configModel.getActualDataNodes();
        log.info("刷新 sharding 配置，table:【{}】,actualDataNodes【{}】", logicTableName, actualDataNodes);
        try {
            ShardingRuntimeContext shardingRuntimeContext = dataSource.getRuntimeContext();
            ShardingRule shardingRule = shardingRuntimeContext.getRule();
            TableRule tableRule = shardingRule.getTableRule(logicTableName);
            ShardingRuleConfiguration shardingRuleRuleConfiguration = shardingRule.getRuleConfiguration();
            Optional<TableRuleConfiguration> tableRuleConfigurationOptional = shardingRuleRuleConfiguration.getTableRuleConfigs().stream().filter(config -> config.getLogicTable().equals(logicTableName)).findFirst();
            if (tableRuleConfigurationOptional.isPresent()) {
                TableRuleConfiguration tableRuleConfig = tableRuleConfigurationOptional.get();
                modifyField(actualDataNodes, tableRuleConfig, "actualDataNodes");
                TableRule newTableRule = new TableRule(tableRuleConfig, shardingRule.getShardingDataSourceNames(), getDefaultGenerateKeyColumn(shardingRuleRuleConfiguration));
                // 修改 actualDataNodes
                modifyField(newTableRule.getActualDataNodes(), tableRule, "actualDataNodes");
                // 修改 actualTables
                modifyField(getActualTables(newTableRule.getActualDataNodes()), tableRule, "actualTables");
                // 修改 dataNodeIndexMap
                List<String> dataNodes = new InlineExpressionParser(tableRuleConfig.getActualDataNodes()).splitAndEvaluate();
                Map<String, Integer> dataNodeIndexMap = new HashMap<>(dataNodes.size(), 1);
                modifyField(dataNodeIndexMap, tableRule, "dataNodeIndexMap");
                // 修改 datasourceToTablesMap
                modifyField(newTableRule.getDatasourceToTablesMap(), tableRule, "datasourceToTablesMap");
            } else {
                throw new RuntimeException("逻辑表" + logicTableName + "不存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("刷新 sharding 配置完成");
    }

    private void modifyField(Object value, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        boolean accessible = field.isAccessible();
        try {
            ReflectionUtils.makeAccessible(field);
            field.set(object, value);
        } finally {
            field.setAccessible(accessible);
        }
    }

    private String getDefaultGenerateKeyColumn(final ShardingRuleConfiguration shardingRuleConfig) {
        return Optional.ofNullable(shardingRuleConfig.getDefaultKeyGeneratorConfig()).map(KeyGeneratorConfiguration::getColumn).orElse(null);
    }

    private Set<String> getActualTables(List<DataNode> actualDataNodes) {
        return actualDataNodes.stream().map(DataNode::getTableName).collect(Collectors.toSet());
    }
}