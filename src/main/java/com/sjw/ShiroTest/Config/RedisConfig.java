package com.sjw.ShiroTest.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
public class RedisConfig{

    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setUsePool(true);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate txRedisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate txRedisTemplate = new RedisTemplate();
        txRedisTemplate.setConnectionFactory(connectionFactory);
        txRedisTemplate.setEnableTransactionSupport(true);
        return txRedisTemplate;
    }

    @Bean
    public JdkSerializationRedisSerializer jdkSerializer() {
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        return jdkSerializer;
    }

}
