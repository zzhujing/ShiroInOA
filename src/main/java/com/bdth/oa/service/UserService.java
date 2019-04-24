package com.bdth.oa.service;

import com.bdth.oa.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
public interface UserService extends IService<User> {

    User getByAccount(String account);
}
