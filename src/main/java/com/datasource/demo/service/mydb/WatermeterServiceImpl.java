package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.WatermeterEntity;
import com.datasource.demo.mapper.mydb.WatermeterMapper;
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
public class WatermeterServiceImpl extends ServiceImpl<WatermeterMapper, WatermeterEntity> implements WatermeterService {

}
