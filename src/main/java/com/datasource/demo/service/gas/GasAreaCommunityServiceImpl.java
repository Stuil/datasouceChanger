package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.gas.GasAreaCommunityEntity;
import com.datasource.demo.mapper.gas.GasAreaCommunityMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class GasAreaCommunityServiceImpl extends ServiceImpl<GasAreaCommunityMapper, GasAreaCommunityEntity> implements GasAreaCommunityService {

    @Override
    public Map<String, Object> getListAreaToMap() {
        Map<String, Object> map = new HashMap<>();
        this.list().forEach(item -> {
            map.put(item.getId(), item.getName());
        });
        return map;
    }
}
