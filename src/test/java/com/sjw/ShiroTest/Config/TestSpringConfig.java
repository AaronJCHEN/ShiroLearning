package com.sjw.ShiroTest.Config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {
        "com.sjw.ShiroTest.Controller",
        "com.sjw.ShiroTest.ServiceImpl"})
public class TestSpringConfig {
    @Bean
    public DruidDataSource druidDataSource () throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:sqlite:c:\\sjwdownload\\data.sqlite");
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setFilters("stat,log4j2");
        dataSource.setMaxActive(20);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(false);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        return dataSource;
    }
}
