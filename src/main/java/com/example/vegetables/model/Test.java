
package com.example.vegetables.model;

import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.vegetables.model.BaseEntity;

import java.io.Serializable;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 朱归华
 * @since 2022-05-06
 */
@Data
@Accessors(chain = true)
public class Test {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

//    private Integer sex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date billStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;


    private String msg;

    private Long corpId;
}
