package com.sjw.ShiroTest.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sjw.ShiroTest.Shiro.RealmForShiro;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TestShiroConfig {

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher () {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("MD5");
        return credentialsMatcher;
    }

    @Bean
    public RealmForShiro realmForShiro (HashedCredentialsMatcher credentialsMatcher, DruidDataSource dataSource) {
        RealmForShiro realmForShiro = new RealmForShiro();
        realmForShiro.setCredentialsMatcher(credentialsMatcher);
        realmForShiro.setPermissionsLookupEnabled(true);
        realmForShiro.setDataSource(dataSource);
        return realmForShiro;
    }

    @Bean
    public SimpleCookie simpleCookie () {
        SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
        rememberMeCookie.setHttpOnly(false);
        rememberMeCookie.setMaxAge(8640);
        return rememberMeCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        rememberMeManager.setCookie(rememberMeCookie);
        return rememberMeManager;
    }

    @Bean
    public ServletContainerSessionManager serlvetSessionMgr () {
        ServletContainerSessionManager servletSessionMgr = new ServletContainerSessionManager();
        return servletSessionMgr;
    }

    @Bean
    public DefaultWebSecurityManager securityManager (
            ServletContainerSessionManager servletSessionMgr,
            RealmForShiro realmForShiro,
            RememberMeManager rememberMeManager,
            EhCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(servletSessionMgr);
        securityManager.setRealm(realmForShiro);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor (DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (DefaultSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChain = new HashMap<>();
        filterChain.put("/auth/*","anon");
        filterChain.put("/druid/*","authc,roles[MANAGER]");
        filterChain.put("/admin/*","authc,roles[MANAGER]");
        filterChain.put("/index/*","authc");
        filterChain.put("/product/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        return shiroFilterFactoryBean;
    }

}
