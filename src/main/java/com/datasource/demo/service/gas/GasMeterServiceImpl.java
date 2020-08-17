package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasMeterEntity;
import com.datasource.demo.mapper.gas.GasMeterMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service

public class GasMeterServiceImpl extends ServiceImpl<GasMeterMapper, GasMeterEntity> implements GasMeterService {

    @Override
    public boolean insertMeter(GasMeterEntity gasMeterEntity) {
        return this.saveOrUpdate(gasMeterEntity);
    }
}
