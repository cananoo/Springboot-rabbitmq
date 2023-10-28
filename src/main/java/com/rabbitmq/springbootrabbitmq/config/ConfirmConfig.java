package com.rabbitmq.springbootrabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布确认高级
 */
@Configuration
public class ConfirmConfig {
    //交换机名称
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    //队列名称
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    //routingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){

        return  new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){

        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }


    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue queue,
                                        @Qualifier("confirmExchange") DirectExchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }

}
