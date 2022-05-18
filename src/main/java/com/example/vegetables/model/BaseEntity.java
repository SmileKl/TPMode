package com.example.vegetables.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 基础实体类
 * @create: 2022-03-02
 **/
@Data
public abstract class BaseEntity<T extends Model> extends Model {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    private String createPeople;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改人
     */
    private String modifyPeople;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, update="NOW()")
    private Date modifyTime;

    /**
     * 0 正常 1 删除
     */
    @TableLogic
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
