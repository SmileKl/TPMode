
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
* @since 2022-10-10
*/
    @Data
        @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    @TableName("area")
    public class Area extends BaseEntity<Area> {

private static final long serialVersionUID = 1L;

    private String label;

    private Integer pid;

    private String value;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
