package com.bdth.oa.shiro.factory;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bdth.oa.domain.*;
import com.bdth.oa.mapper.*;
import com.bdth.oa.utils.SpringContextHolder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

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
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId).select(User::getName));
        if (user != null && StringUtils.isNotEmpty(user.getName())) {
            return user.getName();
        }
        return "";
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
    }


    @Override
    public String getUserAccountById(Integer userId) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, userId)).getAccount();
    }

    @Override
    public String getRoleName(String roleIds) {
        List<Integer> idList = Arrays.stream(roleIds.split(",")).map(Integer::new).collect(toList());
        return idList.stream().map(this::getSingleRoleName).collect(joining(","));
    }

    @Override
    public String getSingleRoleName(Integer roleId) {
        Role role = roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, roleId).select(Role::getTips));
        if (role != null && StringUtils.isNotEmpty(role.getTips())) {
            return role.getTips();
        }
        return "";
    }

    @Override
    public String getSingleRoleTip(Integer roleId) {
        Role role = roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getId, roleId).select(Role::getTips));
        if (role != null && StringUtils.isNotEmpty(role.getName())) {
            return role.getName();
        }
        return "";
    }

    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        return roleMapper.findPermissionByRoleId(roleId);
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
        List<Integer> idList = Arrays.stream(menuIds.split(",")).map(Integer::new).collect(toList());
        return idList.stream().map(this::getMenuName).collect(joining(","));
    }

    @Override
    public String getMenuName(Integer menuId) {
        return menuMapper.selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getId, menuId).select(Menu::getName)).getName();
    }

    @Override
    public String getMenuNameByCode(String code) {
        return menuMapper.selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getCode, code).select(Menu::getName)).getName();
    }

    @Override
    public String getDictName(Integer dictId) {
        return dictMapper.selectOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getId, dictId).select(Dict::getName)).getName();
    }

    @Override
    public String getNoticeTitle(Integer dictId) {
        return null;
    }

    /**
     * 根据字典name和其子节点的value获取其对应子节点name
     *
     * @param name
     * @param val
     * @return
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        LambdaQueryWrapper<Dict> queryWrapper = Wrappers.<Dict>lambdaQuery();
        Dict dict = dictMapper.selectOne(queryWrapper.eq(Dict::getName, name));
        if (dict != null) {
            List<Dict> dicts = dictMapper.selectList(queryWrapper.eq(Dict::getPid, dict.getId()));
            if (dicts != null) {
                return dicts.stream().filter(d -> d.getNum().equals(val)).findAny().map(Dict::getName).orElse("");
            }
        }
        return "";
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
        return deptMapper.selectList(Wrappers.<Dept>lambdaQuery()
                .eq(Dept::getPid, deptid))
                .stream()
                .map(Dept::getId)
                .collect(toList());
    }

    @Override
    public List<Integer> getParentDeptIds(Integer deptid) {
        Dept dept = deptMapper.selectOne(Wrappers.<Dept>lambdaQuery().eq(Dept::getId, deptid));
        if (dept != null && StringUtils.isNotEmpty(dept.getPids())) {
            String[] pidArr = dept.getPids().split(",");
            if (ArrayUtils.isNotEmpty(pidArr)) {
                return Arrays.stream(pidArr)
                        .map(pid -> StringUtils.remove(pid, "["))
                        .map(pid -> StringUtils.remove(pid, "]"))
                        .map(Integer::new)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>(0);
    }
}
