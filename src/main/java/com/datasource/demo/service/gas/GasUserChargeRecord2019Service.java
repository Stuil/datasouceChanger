package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasUserChargeRecord2019Entity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-16
 */
public interface GasUserChargeRecord2019Service extends IService<GasUserChargeRecord2019Entity> {

    boolean insert2019(List<GasUserChargeRecord2019Entity> entityList);
}
