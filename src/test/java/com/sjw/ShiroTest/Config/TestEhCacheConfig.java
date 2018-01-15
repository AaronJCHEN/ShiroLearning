package com.sjw.ShiroTest.Config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestEhCacheConfig {
    @Bean
    public EhCacheManagerFactoryBean ehcacheManager () {
        EhCacheManagerFactoryBean ehcacheManagerBean = new EhCacheManagerFactoryBean();
        ehcacheManagerBean.setShared(true);
        return ehcacheManagerBean;
    }

    @Bean
    public EhCacheManager cacheManager (EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }
}
