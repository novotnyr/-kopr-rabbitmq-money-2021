package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CzkPaymentConsumer {
    public static final Logger logger = LoggerFactory.getLogger(CzkPaymentConsumer.class);

    @RabbitListener(queues = "czk")
    public void handleCzkPayment(Payment payment) {
        logger.info("Consuming payment in CZK: {} Kč", payment.getAmount());
        if (payment.getAmount().signum() == -1) {
            throw new AmqpRejectAndDontRequeueException("Payment cannot be negative");
        }
    }
}
