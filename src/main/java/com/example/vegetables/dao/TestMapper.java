package com.example.vegetables.dao;

import com.example.vegetables.model.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 朱归华
 * @since 2022-05-06
 */
@Repository
public interface TestMapper extends BaseMapper<Test> {

    Boolean batchInsertOrUpdate(List<Test> list);

    Integer countNum();

    List<Test> listTest();

    List<Test> testLeft();
    List<Test> testRight();
    List<Map<String,String>> test_dd();
}
