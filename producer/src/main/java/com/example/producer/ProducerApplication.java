package com.example.producer;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication {
    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    TopicExchange paymentExchange() {
        return ExchangeBuilder.topicExchange("payment").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
