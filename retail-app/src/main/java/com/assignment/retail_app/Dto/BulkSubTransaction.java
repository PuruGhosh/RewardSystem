package com.assignment.retail_app.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BulkSubTransaction {
    private BigDecimal amount;
    private LocalDate transactionDate;
}
