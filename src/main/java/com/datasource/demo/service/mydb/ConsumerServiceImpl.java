package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.ConsumerEntity;
import com.datasource.demo.mapper.mydb.ConsumerMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, ConsumerEntity> implements ConsumerService {

}
