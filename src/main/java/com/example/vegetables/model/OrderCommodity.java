
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
 * 订单-商品关联表
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("skl_order_commodity")
public class OrderCommodity extends BaseEntity<OrderCommodity> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品code
     */
    private Integer commodityCode;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品数量
     */
    private Integer commodityCount;

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
