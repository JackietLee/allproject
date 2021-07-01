package com.itjay.springshiro.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublishSubscribeConfig {

    @Bean
    public Queue myQueue1() {
        Queue queue = new Queue("queue1");
        return queue;
    }

    @Bean
    public Queue myQueue2() {
        Queue queue = new Queue("queue2");
        return queue;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("fanout");
        return fanoutExchange;
    }

    @Bean
    public Binding binding1() {
        Binding binding = BindingBuilder.bind(myQueue1()).to(fanoutExchange());
        return binding;
    }

    @Bean
    public Binding binding2() {
        Binding binding = BindingBuilder.bind(myQueue2()).to(fanoutExchange());
        return binding;
    }

}