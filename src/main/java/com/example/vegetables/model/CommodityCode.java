
package com.example.vegetables.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.vegetables.model.BaseEntity;

import java.io.Serializable;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品code表
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("skl_commodity_code")
public class CommodityCode extends BaseEntity<CommodityCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品code
     */
    private Integer code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 单价
     */
    private Long unitPrice;

    /**
     * 单位
     */
    private String unit;

    /**
     * 修改时间
     */
    @TableField("modifyTime")
    private Date modifytime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
