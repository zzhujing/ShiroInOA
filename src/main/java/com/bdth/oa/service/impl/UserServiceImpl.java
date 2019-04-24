package com.bdth.oa.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bdth.oa.domain.User;
import com.bdth.oa.mapper.UserMapper;
import com.bdth.oa.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByAccount(String account) {
        return baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount,account));
    }
}
