package com.rabbitmq.springbootrabbitmq.consumer;

//rabbitmq的信道 别导错包
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.util.Date;

@Slf4j
@Component
public class DeadLetterQueConsumer {
    //接收消息
    @RabbitListener(queues = "QD")
    public void ReceiveD(Message message, Channel channel) throws  Exception{
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列的消息：{}",new Date().toString(),msg);
    }
}
