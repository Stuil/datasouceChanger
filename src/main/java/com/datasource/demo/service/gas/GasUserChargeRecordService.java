package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasUserChargeRecordEntity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasUserChargeRecordService extends IService<GasUserChargeRecordEntity> {
    boolean insertCharge(List<GasUserChargeRecordEntity> gasUserChargeRecordEntities);
}
