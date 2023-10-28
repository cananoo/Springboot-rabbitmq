package com.rabbitmq.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    // 由于实现的是类中接口，应该将MyCallBack注入到RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //类初始化时执行 construct > @Autowired > @PostConstruct
    @PostConstruct
    public void init(){
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机确认回调方法(成功或者失败都需要调用此方法)
     * @param correlationData 保存回调消息的ID及相关信息
     * @param ack 交换机是否收到消息
     * @param cause 失败的原因 成功未null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
     String id = correlationData != null ?  correlationData.getId() : "";
         if (ack) {
             log.info("交换机已经收到id为{}的消息", id);
         }else {
             log.info("交换机未收到id为：{}的消息,原因：{}", id,cause);
         }
    }

    /**
     * 交换机路由回退方法 (路由失败时才进行回退)
     * @param message the returned message.
     * @param replyCode the reply code.
     * @param replyText the reply text.
     * @param exchange the exchange.
     * @param routingKey the routing key.
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息{}被交换机{}给退回了，原因为：{}，路由key：{}",new String(message.getBody()),exchange,replyText,replyCode);
    }
}
