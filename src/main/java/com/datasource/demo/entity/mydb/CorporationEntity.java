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
    @TableName("corporation")
    public class CorporationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "Co_id", type = IdType.AUTO)
    private Integer coId;

        @TableField("Co_name")
    private String coName;

        @TableField("Co_num")
    private Integer coNum;

        @TableField("Co_AlertBench")
    private Integer coAlertbench;

        @TableField("Co_MaxBuyOnce")
    private Integer coMaxbuyonce;

        @TableField("Co_Maxhording")
    private Integer coMaxhording;

        @TableField("Co_MaxOverDraft")
    private Integer coMaxoverdraft;

        @TableField("Co_PreSaving")
    private Integer coPresaving;

        @TableField("Co_SysKey")
    private Integer coSyskey;

        @TableField("Co_Remark")
    private String coRemark;

        @TableField("Co_Addr")
    private String coAddr;

        @TableField("Co_CreateTime")
    private LocalDateTime coCreatetime;

        @TableField("Co_Updatetime")
    private LocalDateTime coUpdatetime;


}
