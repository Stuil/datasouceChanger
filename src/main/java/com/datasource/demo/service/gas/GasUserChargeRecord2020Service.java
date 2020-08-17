package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasUserChargeRecord2020Entity;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-16
 */
public interface GasUserChargeRecord2020Service extends IService<GasUserChargeRecord2020Entity> {
    boolean insert2020(List<GasUserChargeRecord2020Entity> entityList);
}
