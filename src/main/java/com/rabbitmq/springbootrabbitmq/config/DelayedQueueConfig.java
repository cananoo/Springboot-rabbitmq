package com.rabbitmq.springbootrabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";

    // 声名交换机
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        /**
         * 1.名称
         * 2.类型
         * 3.持久化
         * 4.自动删除
         * 5.其他参数
         */
        return  new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message",true,false,args);
    }

    // 声名队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //绑定
    @Bean
    public Binding delayedQueueBingDelayedExchange(@Qualifier("delayedQueue") Queue queue,
                                                   @Qualifier("delayedExchange") CustomExchange exchange ){    //没有定义名称默认为方法名
        return BindingBuilder.bind(queue).to(exchange).with(DELAYED_ROUTING_KEY).noargs(); //noargs() 为构建方法--自定义交换机使用
    }

}
