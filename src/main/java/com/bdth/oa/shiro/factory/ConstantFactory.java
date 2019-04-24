package com.bdth.oa.shiro.factory;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bdth.oa.domain.*;
import com.bdth.oa.mapper.*;
import com.bdth.oa.utils.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 常量的生产工厂,批量处理
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {
    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }


    @Override
    public String getUserNameById(Integer userId) {
        return null;
    }

    @Override
    public String getUserAccountById(Integer userId) {
        return null;
    }

    @Override
    public String getRoleName(String roleIds) {
        return null;
    }

    @Override
    public String getSingleRoleName(Integer roleId) {
        Role role = roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, roleId).select(Role::getName));
        if (role != null && StringUtils.isNotEmpty(role.getName())) {
            return role.getName();
        }
        return "";
    }

    @Override
    public String getSingleRoleTip(Integer roleId) {
        return null;
    }


    @Override
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectOne(Wrappers.<Dept>lambdaQuery().eq(Dept::getId, deptId).select(Dept::getFullname));
        if (dept != null && StringUtils.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    @Override
    public String getMenuNames(String menuIds) {
        return null;
    }

    @Override
    public String getMenuName(Integer menuId) {
        return null;
    }

    @Override
    public String getMenuNameByCode(String code) {
        return null;
    }

    @Override
    public String getDictName(Integer dictId) {
        return null;
    }

    @Override
    public String getNoticeTitle(Integer dictId) {
        return null;
    }

    @Override
    public String getDictsByName(String name, Integer val) {
        return null;
    }

    @Override
    public String getSexName(Integer sex) {
        return null;
    }

    @Override
    public String getStatusName(Integer status) {
        return null;
    }

    @Override
    public String getMenuStatusName(Integer status) {
        return null;
    }

    @Override
    public List<Dict> findInDict(Integer id) {
        return null;
    }

    @Override
    public String getCacheObject(String para) {
        return null;
    }

    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        return null;
    }

    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        return null;
    }
}
