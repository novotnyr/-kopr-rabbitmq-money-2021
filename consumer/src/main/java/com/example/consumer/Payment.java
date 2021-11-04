package com.example.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Payment {
    private final BigDecimal amount;

    public Payment(@JsonProperty("amount") BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
