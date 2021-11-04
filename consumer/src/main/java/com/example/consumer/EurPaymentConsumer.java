package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EurPaymentConsumer {
    public static final Logger logger = LoggerFactory.getLogger(EurPaymentConsumer.class);

    @RabbitListener(queues = "eur", errorHandler = "negativePaymentErrorHandler")
    public void handlePayment(Payment payment) {
        logger.info("Consuming payment in EUR: {} €", payment.getAmount());
        if (payment.getAmount().signum() == -1) {
            throw new NegativePaymentException(payment.getAmount());
        }
    }

    @Component("negativePaymentErrorHandler")
    public static class NegativePaymentErrorHandler implements RabbitListenerErrorHandler {
        @Override
        public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
            if (exception.getCause() instanceof NegativePaymentException) {
                throw new AmqpRejectAndDontRequeueException(exception.getMessage(), exception.getCause());
            } else {
                throw exception;
            }
        }
    }
}
