
package com.example.vegetables.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.vegetables.model.BaseEntity;

import java.io.Serializable;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 朱归华
 * @since 2022-11-22
 */
@Data
@Accessors(chain = true)
@TableName("category")
public class Category{

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 父节点id
     */
    private Integer pid;

    private Long projectId;
}
