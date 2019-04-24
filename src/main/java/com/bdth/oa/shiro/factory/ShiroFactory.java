package com.bdth.oa.shiro.factory;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bdth.oa.domain.User;
import com.bdth.oa.mapper.RoleMapper;
import com.bdth.oa.mapper.UserMapper;
import com.bdth.oa.shiro.ShiroUser;
import com.bdth.oa.shiro.state.ManagerStatus;
import com.bdth.oa.utils.SpringContextHolder;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactory implements IShiro {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;
    private  final IConstantFactory constantFactory = ConstantFactory.me();

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public User user(String account) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId(user.getId());            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setDeptId(user.getDeptid());    // 部门id
        shiroUser.setDeptName(ConstantFactory.me().getDeptName(user.getDeptid()));// 部门名称
        shiroUser.setName(user.getName());        // 用户名称
        List<Integer> roleList = Stream.of(user.getRoleid()).map(Integer::parseInt).collect(toList()); //角色集合
        shiroUser.setRoleList(roleList);
         //根据角色ID获取角色名称集合
        List<String> roleNameList = roleList.stream().map(constantFactory::getSingleRoleName).collect(toList());
        shiroUser.setRoleNames(roleNameList);
        return shiroUser;
    }

    /**
     * 根据角色ID获取所有的资源url
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<String> findPermissionsByRoleId(Integer roleId) {
        return roleMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public String findRoleNameByRoleId(Integer roleId) {
        return constantFactory.getSingleRoleName(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
