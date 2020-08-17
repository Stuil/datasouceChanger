package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasMendGasEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasMendGasService extends IService<GasMendGasEntity> {
    boolean insertMendGas(List<GasMendGasEntity> gasMendGasEntity);
}
