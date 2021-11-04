package com.example.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    @Autowired
    AmqpTemplate amqpTemplate;

    @Scheduled(fixedRate = 2000)
    public void pay() {
        // zaplatim X eur
        // do exchange 'payment' poslem spravu
        Payment payment = new Payment();
        payment.setAmount(200);

        amqpTemplate.convertAndSend("payment", "payment.eur", payment);
    }
}
