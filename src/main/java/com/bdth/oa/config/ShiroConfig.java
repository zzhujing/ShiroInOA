package com.bdth.oa.config;

import com.bdth.oa.shiro.ShiroDbRealm;
import com.bdth.oa.shiro.ShiroProperties;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author hj
 * @create 2019-04-23 9:32
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfig {

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager webSecurityManager(CacheManager cacheShiroManager,ShiroDbRealm shiroDbRealm, CookieRememberMeManager rememberMeManager,DefaultWebSessionManager defaultWebSessionManager) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setRememberMeManager(rememberMeManager);
        webSecurityManager.setSessionManager(defaultWebSessionManager);
        webSecurityManager.setCacheManager(cacheShiroManager);
        webSecurityManager.setRealm(shiroDbRealm);
        return webSecurityManager;
    }


    /**
     * session管理器
     */
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(CacheManager cacheShiroManager, ShiroProperties shiroProperties) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(cacheShiroManager);
        //设置session失效时间
        sessionManager.setSessionValidationInterval(shiroProperties.getSessionValidationInterval() * 1000); //30分钟
        //开启session的定时清理，15分钟一次
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionInvalidateTime() * 1000); //15分钟默认
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }


    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public CacheManager getCacheShiroManager(EhCacheManagerFactoryBean ehcache) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehcache.getObject());
        return ehCacheManager;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        //使用base64加密了
        manager.setCipherKey(Base64.decode("b2EAAAAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(7 * 24 * 60 * 60);//7天
        return simpleCookie;
    }


    /**
     * 核心过滤器配置
     *
     * @param webSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager webSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
       /*
        添加登录认证
        non:匿名访问，无需认证
        authc:认证
        perms:权限,ex: perms[user:add] 需要user:add权限
        logout:登出
        */
        Map<String, String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/**", "user");
        shiroFilterFactoryBean.setLoginUrl("/login");   //设置认证跳转url
        shiroFilterFactoryBean.setSuccessUrl("/");   //认证成功跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/global/error"); //未授权跳转页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroDbRealm shiroDbRealm() {
        return new ShiroDbRealm();
    }


    /**
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
