package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.WaterpriceEntity;
import com.datasource.demo.mapper.mydb.WaterpriceMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用气类型表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class WaterpriceServiceImpl extends ServiceImpl<WaterpriceMapper, WaterpriceEntity> implements WaterpriceService {

}
