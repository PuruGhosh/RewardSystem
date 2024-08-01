package com.assignment.retail_app.Dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RewardResponse {
    private UUID transactionId;
    private BigDecimal transactionAmount;
    private int points;
    private LocalDateTime awardedDate;
}
