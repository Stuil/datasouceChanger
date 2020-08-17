package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasUserEntity;
import com.datasource.demo.mapper.gas.GasUserMapper;
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

public class GasUserServiceImpl extends ServiceImpl<GasUserMapper, GasUserEntity> implements GasUserService {


    @Override
    public boolean insetUser(GasUserEntity gasUserEntity) {
        return this.saveOrUpdate(gasUserEntity);
    }
}
