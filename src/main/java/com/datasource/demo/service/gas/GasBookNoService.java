package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasBookNoEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasBookNoService extends IService<GasBookNoEntity> {

    GasBookNoEntity getBooks(String communityId);
}
