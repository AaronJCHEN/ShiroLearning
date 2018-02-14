package com.sjw.ShiroTest.Config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sjw.ShiroTest.Shiro.*;
import com.sjw.ShiroTest.Utils.JWTUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
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
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    @Bean
    public JWTUtils jwtUtils() {
        return new JWTUtils();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        return lifecycleBeanPostProcessor;
    }

    @Bean
    public JWTShiroFilter jwtShiroFilter() {
        JWTShiroFilter jwtShiroFilter = new JWTShiroFilter();
        return jwtShiroFilter;
    }

    @Bean
    public DefaultSessionStorageEvaluator defaultSessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        return defaultSessionStorageEvaluator;
    }

    @Bean
    public DefaultSubjectDAO defaultSubjectDAO(DefaultSessionStorageEvaluator defaultSessionStorageEvaluator) {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }

    @Bean
    public JWTRealm jwtRealm () {
        JWTRealm jwtRealm = new JWTRealm();
        jwtRealm.setPermissionsLookupEnabled(true);
        return jwtRealm;
    }

    @Bean
    public JWTSubjectFactory jwtSubjectFactory() {
        JWTSubjectFactory jwtSubjectFactory = new JWTSubjectFactory();
        return jwtSubjectFactory;
    }

    @Bean
    public DefaultWebSecurityManager securityManager (
            JWTRealm jwtRealm,
            EhCacheManager cacheManager,
            JWTSubjectFactory jwtSubjectFactory,
            DefaultSubjectDAO subjectDAO) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jwtRealm);
        securityManager.setCacheManager(cacheManager);

        //close session
        securityManager.setSubjectFactory(jwtSubjectFactory);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultSecurityManager securityManager){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager);
        return methodInvokingFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor (DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (
            DefaultSecurityManager securityManager,
            JWTShiroFilter jwtShiroFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwtShiroFilter", jwtShiroFilter);
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String, String> filterChain = new LinkedHashMap<>();
        filterChain.put("/ShiroTest/auth/*","anon");
        filterChain.put("/ShiroTest/druid/*","jwtShiroFilter,roles[MANAGER]");
        filterChain.put("/ShiroTest/admin/*","jwtShiroFilter,roles[MANAGER]");
        filterChain.put("/ShiroTest/index/*","jwtShiroFilter");
        filterChain.put("/ShiroTest/product/*","jwtShiroFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

}
