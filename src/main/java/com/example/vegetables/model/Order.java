
package com.example.vegetables.model;

import java.math.BigDecimal;

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
 * 订单
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("skl_order")
public class Order extends BaseEntity<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 支付订单编号
     */
    private String payCode;

    /**
     * 总金额
     */
    private BigDecimal amountMoney;

    /**
     * 应付
     */
    private BigDecimal allMoney;

    /**
     * 消费者code
     */
    private String personCode;

    /**
     * 配送地址
     */
    private String distributionAddress;

    /**
     * 销售单位
     */
    private String salesUnit;

    /**
     * 订单状态 1 待付款 2 待分拣 3 待配送 4配送完成
     */
    private Integer orderStatus;

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
