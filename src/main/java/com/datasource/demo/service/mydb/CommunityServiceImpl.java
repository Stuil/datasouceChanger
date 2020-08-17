package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.CommunityEntity;
import com.datasource.demo.mapper.mydb.CommunityMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 小区表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, CommunityEntity> implements CommunityService {

    @Override
    public Map<Integer, String> getOneCommunity() {
        Map<Integer, String> map = new HashMap<>();
        this.list().forEach(item -> {
            map.put(item.getCommId(), item.getCommName());
        });
        return map;
    }
}
