package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasUserChargeRecordEntity;
import com.datasource.demo.mapper.gas.GasUserChargeRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service

public class GasUserChargeRecordServiceImpl extends ServiceImpl<GasUserChargeRecordMapper, GasUserChargeRecordEntity> implements GasUserChargeRecordService {

    @Override
    public boolean insertCharge(List<GasUserChargeRecordEntity> gasUserChargeRecordEntities) {
        return this.saveOrUpdateBatch(gasUserChargeRecordEntities);
    }
}
