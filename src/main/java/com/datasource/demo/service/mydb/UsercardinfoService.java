package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.mydb.UsercardinfoEntity;

import java.util.List;

/**
 * <p>
 * 用户卡信息 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface UsercardinfoService extends IService<UsercardinfoEntity> {

    List<UsercardinfoEntity> selectCard();
}
