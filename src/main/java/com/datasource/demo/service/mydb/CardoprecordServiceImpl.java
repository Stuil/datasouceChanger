package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.CardoprecordEntity;
import com.datasource.demo.mapper.mydb.CardoprecordMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 卡片记录，购气，补气、退气表 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class CardoprecordServiceImpl extends ServiceImpl<CardoprecordMapper, CardoprecordEntity> implements CardoprecordService {

}
