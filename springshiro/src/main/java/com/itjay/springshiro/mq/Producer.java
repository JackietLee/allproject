package com.itjay.springshiro.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendMail(String queue, String msg) {
        rabbitTemplate.convertAndSend(queue,msg);
    }
}
