package com.datasource.demo.entity.mydb;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @TableName("log")
    public class LogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "L_id", type = IdType.AUTO)
    private Integer lId;

        @TableField("L_employeeid")
    private Integer lEmployeeid;

        @TableField("L_content")
    private String lContent;

        @TableField("L_CreateTime")
    private LocalDateTime lCreatetime;

        @TableField("L_Type")
    private Integer lType;

        @TableField("L_res1")
    private String lRes1;

        @TableField("L_res2")
    private String lRes2;

        @TableField("L_res3")
    private String lRes3;


}
