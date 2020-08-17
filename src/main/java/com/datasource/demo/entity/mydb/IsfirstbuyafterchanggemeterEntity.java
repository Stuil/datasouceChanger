package com.datasource.demo.entity.mydb;

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
    @TableName("isfirstbuyafterchanggemeter")
    public class IsfirstbuyafterchanggemeterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId("FBACM_id")
    private Integer fbacmId;

        @TableField("FBACM_isValid")
    private Integer fbacmIsvalid;

        @TableField("FBACM_OPTime")
    private LocalDateTime fbacmOptime;


}
