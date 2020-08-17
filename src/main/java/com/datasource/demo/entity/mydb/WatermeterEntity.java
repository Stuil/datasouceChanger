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
    @TableName("watermeter")
    public class WatermeterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "WaterMeter_id", type = IdType.AUTO)
    private Integer watermeterId;

        @TableField("WM_Type")
    private String wmType;

        @TableField("WM_Name")
    private String wmName;

        @TableField("WM_Remark")
    private String wmRemark;

        @TableField("WM_IsAvailable")
    private Integer wmIsavailable;

        @TableField("WM_CreateTime")
    private LocalDateTime wmCreatetime;

        @TableField("WM_UpDateTime")
    private LocalDateTime wmUpdatetime;

        @TableField("WM_Res1")
    private String wmRes1;

        @TableField("WM_Res2")
    private String wmRes2;

        @TableField("WM_Res3")
    private String wmRes3;


}
