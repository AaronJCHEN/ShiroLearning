package com.sjw.ShiroTest.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sjw.ShiroTest.Shiro.RealmForShiro;
import com.sjw.ShiroTest.Shiro.SessionForRedisDao;
import com.sjw.ShiroTest.Shiro.SessionForRedisFactory;
import com.sjw.ShiroTest.Shiro.SessionStsListener;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public SessionForRedisFactory redisFactory () {
        SessionForRedisFactory redisFactory = new SessionForRedisFactory();
        redisFactory.setKey("SessionList");
        return redisFactory;
    }

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
    public SessionForRedisDao redisDao() {
        SessionForRedisDao redisDao = new SessionForRedisDao();
        redisDao.setOnlyEhCache(false);
        redisDao.setSeconds(300000);
        redisDao.setKey("SessionList");
        return redisDao;
    }

    @Bean
    public SessionStsListener stsListener() {
        SessionStsListener stsListener = new SessionStsListener();
        return stsListener;
    }

    @Bean
    public ExecutorServiceSessionValidationScheduler validationScheduler(){
        ExecutorServiceSessionValidationScheduler validationScheduler = new ExecutorServiceSessionValidationScheduler();
        validationScheduler.setInterval(300000);
        return validationScheduler;
    }

    @Bean
    public DefaultWebSessionManager defaultWebSessionManager (
            SessionForRedisFactory redisFactory,
            SessionForRedisDao redisDao,
            ExecutorServiceSessionValidationScheduler validationScheduler,
            SessionListener stsListener) {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(600000);
        defaultWebSessionManager.setSessionFactory(redisFactory);
        defaultWebSessionManager.setSessionDAO(redisDao);
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
        defaultWebSessionManager.setSessionValidationScheduler(validationScheduler);
        List<SessionListener> stsListeners = new ArrayList<>();
        stsListeners.add(stsListener);
        defaultWebSessionManager.setSessionListeners(stsListeners);
        return defaultWebSessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager (
            DefaultWebSessionManager defaultWebSessionManager,
            RealmForShiro realmForShiro,
            RememberMeManager rememberMeManager,
            EhCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(defaultWebSessionManager);
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
