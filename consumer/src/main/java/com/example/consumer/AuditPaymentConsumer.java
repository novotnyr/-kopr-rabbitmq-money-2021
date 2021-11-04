package com.example.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuditPaymentConsumer {
    public static final Logger logger = LoggerFactory.getLogger(AuditPaymentConsumer.class);

    @RabbitListener(
            bindings = {
                @QueueBinding(
                        value = @Queue("audit"),
                        exchange = @Exchange(name = "payment", type = "topic"),
                        key = "payment.#"
                )
            },
            ackMode = "MANUAL")
    public void auditPayment(Payment payment,
                             Channel channel,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag
    ) throws IOException {
        logger.info("Auditing payment: {}", payment.getAmount());

        if (payment.getAmount().signum() == -1) {
            logger.warn("Payment {} is rejected", payment.getAmount());
            channel.basicReject(deliveryTag, false);
        } else {
            channel.basicAck(deliveryTag, false);
        }
    }
}
