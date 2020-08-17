package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasUserEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasUserService extends IService<GasUserEntity> {

    boolean insetUser(GasUserEntity gasUserEntity);
}
