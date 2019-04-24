package com.bdth.oa.mapper;

import com.bdth.oa.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author hujing
 * @since 2019-04-23
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> findPermissionByRoleId(Integer roleId);
}
