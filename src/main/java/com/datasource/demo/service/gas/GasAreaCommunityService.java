package com.datasource.demo.service.gas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.datasource.demo.entity.gas.GasAreaCommunityEntity;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
public interface GasAreaCommunityService extends IService<GasAreaCommunityEntity> {
    Map<String,Object> getListAreaToMap();

}
