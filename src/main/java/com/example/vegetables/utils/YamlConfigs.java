package com.example.vegetables.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlConfigs {
    private final static DumperOptions OPTIONS = new DumperOptions();
    static {
        //设置yaml读取方式为块读取
        OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        OPTIONS.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        OPTIONS.setPrettyFlow(false);
    }

//    public static void main(String[] args) {
//        String fileName = "src/main/resources/application.yml";
//        String key = "spring.shardingsphere.sharding.tables.test.actual-data-nodes";
//        List<String> list = new ArrayList<>();
//        list.add("/bin/sh");
//        list.add("-ce");
//        list.add("java -jar lizard.jar");
//        boolean flag = updateYaml(key, list, fileName);
//    }

    /**
     * 将Yaml配置文件转换成map
     * @param fileName
     * @return
     */
    public static Map<String, Object> getYamlToMap(String fileName) {
        LinkedHashMap yamls = new LinkedHashMap<>();
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(fileName);
            yamls = yaml.loadAs(inputStream, LinkedHashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yamls;
    }

    /**
     * 根据key获取yaml文件中属性值
     * @param key
     * @param yamlMap
     * @return
     */
    public static Object getValue(String key, Map<String, Object> yamlMap) {
        String[] keys = key.split("[.]");
        Object obj = yamlMap.get(keys[0]);
        if (key.contains(".")) {
            if (obj instanceof Map) {
                return getValue(key.substring(key.indexOf(".") + 1), (Map<String, Object>) obj);
            } else if (obj instanceof List) {
                return getValue(key.substring(key.indexOf(".") + 1), (Map<String, Object>) ((List<?>) obj).get(0));
            } else {
                return null;
            }
        }
        return obj;
    }

    /**
     * 使用递归的方式更改map中的值
     * @param map
     * @param key 指定哪个键
     * @param value 即将更改的值
     * @return
     */
    public static Map<String, Object> setValue(Map<String, Object> map, String key, Object value) throws Exception {
        String[] keys = key.split("\\.");
        int len = keys.length;
        Map temp = map;
        for (int i = 0; i < len - 1; i++) {
            if (temp.containsKey(keys[i])) {
                Object obj = temp.get(keys[i]);
                if (obj instanceof Map) {
                    temp = (Map) obj;
                } else if (obj instanceof List) {
                    temp = (Map) ((List<?>) obj).get(0);
                } else {
                    throw new Exception("temp类型错误");
                }
            } else {
                return null;
            }
            if (i == len - 2) {
                temp.put(keys[i + 1], value);
            }
        }
        for (int j = 0; j < len - 1; j++) {
            if (j == len - 1) {
                map.put(keys[j], temp);
            }
        }
        return map;
    }

    /**
     * 修改yaml中属性的值
     * @param key key是properties的方式： aaa.bbb.ccc (key不存在不修改)
     * @param value 新的属性值 （新属性值和旧属性值一样，不修改）
     * @param yamlName
     * @return true 修改成功，false 修改失败
     */
    public static boolean updateYaml(String key, Object value, String yamlName) {
        Map<String, Object> yamlToMap = getYamlToMap(yamlName);
        if (null == yamlToMap) {
            return false;
        }

        Object oldVal = getValue(key, yamlToMap);

        // 未找到key，不修改
        if (null == oldVal) {
            System.out.println("key is not found!");
            return false;
        }

        // 不是最小节点值，不修改
        if (oldVal instanceof Map) {
            System.out.println("input key is not last node!");
            return false;
        }

        if (value == oldVal) {
            System.out.println("newVal equals oldVal!");
            return false;
        }
        Yaml yaml = new Yaml(OPTIONS);
        String path = "src/main/resources/application.yml";
        try {
            Map<String, Object> resultMap = setValue(yamlToMap, key, value);
            if (resultMap != null) {
                yaml.dump(setValue(yamlToMap, key, value), new FileWriter(path));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
