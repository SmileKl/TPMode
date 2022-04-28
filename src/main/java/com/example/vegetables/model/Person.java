
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
 * 欠款信息
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("skl_person")
public class Person extends BaseEntity<Person> {

    private static final long serialVersionUID = 1L;

    /**
     * 欠款人code
     */
    private String code;

    /**
     * 欠款人姓名
     */
    private String name;

    /**
     * 欠款人手机号码
     */
    private String mobile;

    /**
     * 欠款人地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 欠款金额
     */
    private BigDecimal arrearsMoney;

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
