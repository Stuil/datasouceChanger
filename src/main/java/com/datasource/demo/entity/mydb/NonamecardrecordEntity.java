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
    @TableName("nonamecardrecord")
    public class NonamecardrecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @TableField("NNC_type")
    private String nncType;

        @TableField("NNC_watercount")
    private Integer nncWatercount;

        @TableField("NNC_comment")
    private String nncComment;

        @TableField("NNC_employeeId")
    private Integer nncEmployeeid;

        @TableField("NNC_CreateTime")
    private LocalDateTime nncCreatetime;


}
