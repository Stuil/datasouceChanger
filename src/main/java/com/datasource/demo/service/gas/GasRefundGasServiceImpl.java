package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasRefundGasEntity;
import com.datasource.demo.mapper.gas.GasRefundGasMapper;
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

public class GasRefundGasServiceImpl extends ServiceImpl<GasRefundGasMapper, GasRefundGasEntity> implements GasRefundGasService {

    @Override
    public boolean insetRefund(List<GasRefundGasEntity> refundGasEntities) {
        return this.saveOrUpdateBatch(refundGasEntities);
    }
}
