package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasBookNoEntity;
import com.datasource.demo.mapper.gas.GasBookNoMapper;
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

public class GasBookNoServiceImpl extends ServiceImpl<GasBookNoMapper, GasBookNoEntity> implements GasBookNoService {
    @Override
    public GasBookNoEntity getBooks(String communityId) {
        return this.list(new QueryWrapper<GasBookNoEntity>()
                .eq("communityId",communityId).orderByDesc("id")).get(0);
    }
}
