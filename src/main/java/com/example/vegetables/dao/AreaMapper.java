package com.example.vegetables.dao;

import com.example.vegetables.model.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 朱归华
 * @since 2022-10-10
 */
public interface AreaMapper extends BaseMapper<Area> {

    void insetStr(String sqlStr);
}
