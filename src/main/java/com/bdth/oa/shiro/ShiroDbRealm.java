package com.bdth.oa.shiro;

import com.bdth.oa.domain.User;
import com.bdth.oa.shiro.factory.IShiro;
import com.bdth.oa.shiro.factory.ShiroFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ShiroDbRealm extends AuthorizingRealm {

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        //1.校验用户名密码
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        IShiro shiro = ShiroFactory.me();
        User user = shiro.user(token.getUsername());
        //2.获取该用户对应的权限和资源
        ShiroUser shiroUser = shiro.shiroUser(user);
        //3.返回
        return shiro.info(shiroUser, user, getName());
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取shiroUser
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        List<Integer> roleList = user.getRoleList();
        IShiro shiro = ShiroFactory.me();
        //2.获取权限信息(资源url,角色名称)
        Set<String> urlList = roleList.stream().map(shiro::findPermissionsByRoleId)
                .flatMap(Collection::stream)
                .collect(toSet());
        Set<String> roleNames = roleList.stream().map(shiro::findRoleNameByRoleId).collect(toSet());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(urlList);
        info.setRoles(roleNames);
        return info;
    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }
}
