package com.assignment.retail_app.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


@Data
public class TransactionRequestDto {
    private UUID customerId;
    private BigDecimal amount;
}
