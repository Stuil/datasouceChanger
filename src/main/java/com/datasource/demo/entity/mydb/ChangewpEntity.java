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
    @TableName("changewp")
    public class ChangewpEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "CW_id", type = IdType.AUTO)
    private Integer cwId;

        @TableField("CW_ConsumerId")
    private Integer cwConsumerid;

        @TableField("CW_OldWP_id")
    private Integer cwOldwpId;

        @TableField("CW_NewWP_id")
    private Integer cwNewwpId;

        @TableField("CW_CreateTime")
    private LocalDateTime cwCreatetime;

        @TableField("CW_Remark")
    private String cwRemark;

        @TableField("CW_EmployeeId")
    private Integer cwEmployeeid;


}
