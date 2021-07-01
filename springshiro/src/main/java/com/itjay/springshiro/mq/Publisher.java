package com.itjay.springshiro.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Publisher {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void publishMail(String mail) {
        rabbitTemplate.convertAndSend("fanout", "", mail);
    }

    public void senddirectMail(String mail, String routingkey) {
        rabbitTemplate.convertAndSend("direct", routingkey, mail);
    }

    public void sendtopicMail(String mail, String routingkey) {
        rabbitTemplate.convertAndSend("mytopic", routingkey, mail);
    }
}
