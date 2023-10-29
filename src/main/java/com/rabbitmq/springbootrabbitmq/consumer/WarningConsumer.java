package com.rabbitmq.springbootrabbitmq.consumer;

import com.rabbitmq.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarningConsumer {
     @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
     public void receiveWarningMsg(Message msg){
            log.info("报警发现不可路由消息：{}",new String(msg.getBody()));
        }
}
