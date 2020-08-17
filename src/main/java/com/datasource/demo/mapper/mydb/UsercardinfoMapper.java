package com.datasource.demo.mapper.mydb;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datasource.demo.entity.mydb.UsercardinfoEntity;

import java.util.List;

/**
 * <p>
 * 用户卡信息 Mapper 接口
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */

public interface UsercardinfoMapper extends BaseMapper<UsercardinfoEntity> {

    List<UsercardinfoEntity> selectCard();

}
