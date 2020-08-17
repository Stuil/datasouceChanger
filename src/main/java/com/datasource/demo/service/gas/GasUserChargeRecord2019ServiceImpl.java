package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasUserChargeRecord2019Entity;
import com.datasource.demo.mapper.gas.GasUserChargeRecord2019Mapper;
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

public class GasUserChargeRecord2019ServiceImpl extends ServiceImpl<GasUserChargeRecord2019Mapper, GasUserChargeRecord2019Entity> implements GasUserChargeRecord2019Service {

    @Override
    public boolean insert2019(List<GasUserChargeRecord2019Entity> entityList) {
        return this.saveOrUpdateBatch(entityList);
    }
}
