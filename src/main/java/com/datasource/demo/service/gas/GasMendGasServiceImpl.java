package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasMendGasEntity;
import com.datasource.demo.mapper.gas.GasMendGasMapper;
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

public class GasMendGasServiceImpl extends ServiceImpl<GasMendGasMapper, GasMendGasEntity> implements GasMendGasService {

    @Override
    public boolean insertMendGas(List<GasMendGasEntity> gasMendGasEntity) {
        return this.saveOrUpdateBatch(gasMendGasEntity);
    }
}
