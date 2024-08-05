package com.assignment.retail_app.Dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BulkTransactionRequest {
    private UUID customerId;
    private List<BulkSubTransaction> transactions;
}
