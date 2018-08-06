package com.sjw.ShiroTest.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Watson on 07/20/2018
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue salesQueue(){
        return new Queue("salesQueue");
    }

    @Bean
    public SimpleRabbitListenerContainerFactory salesListenerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer
            , ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(20);
        factory.setConcurrentConsumers(20);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
