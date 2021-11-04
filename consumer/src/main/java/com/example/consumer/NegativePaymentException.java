package com.example.consumer;

import java.math.BigDecimal;

public class NegativePaymentException extends RuntimeException {

    public NegativePaymentException(BigDecimal amount) {
        super("Negative payments (" + amount + ") are not valid");
    }

}