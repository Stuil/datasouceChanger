package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasUserChargeRecord2020Entity;
import com.datasource.demo.mapper.gas.GasUserChargeRecord2020Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-16
 */
@Service

public class GasUserChargeRecord2020ServiceImpl extends ServiceImpl<GasUserChargeRecord2020Mapper, GasUserChargeRecord2020Entity> implements GasUserChargeRecord2020Service {

    @Override
    public boolean insert2020(List<GasUserChargeRecord2020Entity> entityList) {
        return this.saveOrUpdateBatch(entityList);
    }
}
