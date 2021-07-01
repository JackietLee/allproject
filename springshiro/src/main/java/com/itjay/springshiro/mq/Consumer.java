package com.itjay.springshiro.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @RabbitListener(queues = "myqueue")
    public void displayMailSim1(String mail) throws Exception {
        System.out.println("队列监听器displayMailSim1号收到消息"+mail);
    }
    @RabbitListener(queues = "myqueue")
    public void displayMailSim2(String mail) throws Exception {
        System.out.println("队列监听器displayMailSim2号收到消息"+mail);
    }

    @RabbitListener(queues = "directqueue1")
    public void displayMail1(String mail) throws Exception {
        System.out.println("队列监听器directqueue1号收到消息"+mail);
    }

    @RabbitListener(queues = "directqueue2")
    public void displayMail2(String mail) throws Exception {
        System.out.println("队列监听器directqueue2号收到消息"+mail);
    }


    @RabbitListener(queues = "queue1")
    public void displayMail3(String mail) throws Exception {
        System.out.println("队列监听器queue1号收到消息"+mail);
    }

    @RabbitListener(queues = "queue2")
    public void displayMail4(String mail) throws Exception {
        System.out.println("队列监听器queue2号收到消息"+mail);
    }
}
