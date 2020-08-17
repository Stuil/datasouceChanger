package com.datasource.demo.entity.mydb;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 
    * </p>
*
* @author stuil
* @since 2020-08-13
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("tmplst")
    public class TmplstEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer nlevel;

        @TableField("sCort")
    private String sCort;


}
