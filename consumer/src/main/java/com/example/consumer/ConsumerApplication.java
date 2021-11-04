package com.example.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {
    @Bean
    Queue eurQueue() {
        return QueueBuilder.durable("eur").build();
    }

    @Bean
    Queue czkQueue() {
        return QueueBuilder.durable("czk").build();
    }

    @Bean
    TopicExchange paymentExchange() {
        return ExchangeBuilder.topicExchange("payment").build();
    }

    @Bean
    Binding paymentToEurBinding() {
        return BindingBuilder
                .bind(eurQueue())
                .to(paymentExchange())
                .with("payment.eur");
    }

    @Bean
    Binding paymentToCzkBinding() {
        return BindingBuilder
                .bind(czkQueue())
                .to(paymentExchange())
                .with("payment.czk");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
