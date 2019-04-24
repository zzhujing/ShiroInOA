package com.bdth.oa.service.impl;

import com.bdth.oa.domain.LoginLog;
import com.bdth.oa.mapper.LoginLogMapper;
import com.bdth.oa.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
