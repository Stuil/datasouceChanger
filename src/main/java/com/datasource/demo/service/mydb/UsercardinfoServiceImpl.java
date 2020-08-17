package com.datasource.demo.service.mydb;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.datasource.demo.entity.mydb.UsercardinfoEntity;
import com.datasource.demo.mapper.mydb.UsercardinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户卡信息 服务实现类
 * </p>
 *
 * @author stuil
 * @since 2020-08-13
 */
@Service
public class UsercardinfoServiceImpl extends ServiceImpl<UsercardinfoMapper, UsercardinfoEntity> implements UsercardinfoService {
    @Autowired
    UsercardinfoMapper usercardinfoMapper;

    @Override
    public List<UsercardinfoEntity> selectCard() {
        return usercardinfoMapper.selectCard();
    }
}
