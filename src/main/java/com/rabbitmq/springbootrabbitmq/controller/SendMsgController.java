package com.rabbitmq.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

 //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void  sendMsg(@PathVariable String message){
       log.info("当前时间:{},发送一条信息给两个TTL队列:{}",new Date().toString(),message);
       rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列" + message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列" + message);
    }

    @GetMapping("/sendExpirationMsg/{msg}/{ttlTime}")
    public void sendMsg(@PathVariable String msg,@PathVariable String ttlTime){
        log.info("当前时间:{},发送一条时长为{}的信息给队列C:{}",new Date().toString(),ttlTime,msg);
        rabbitTemplate.convertAndSend("X","XC","消息来自C的自定义时长的队列" + msg,message -> {
            //发送消息的延迟时长
            message.getMessageProperties().setExpiration(ttlTime); //单位为ms
            return message;
        });
    }
}
