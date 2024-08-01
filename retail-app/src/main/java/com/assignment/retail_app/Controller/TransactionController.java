package com.assignment.retail_app.Controller;

import com.assignment.retail_app.Dto.TransactionRequest;
import com.assignment.retail_app.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/txn")
public class TransactionController {

  @Autowired private TransactionService transactionService;

  @PostMapping("/createTransaction")
  public ResponseEntity<String> handleTransaction(
      @RequestBody TransactionRequest transactionRequest) {
    if (transactionRequest == null
        || transactionRequest.getAmount() == null
        || transactionRequest.getCustomerId() == null) {
      throw new RuntimeException("Invalid transaction Request");
    }

    transactionService.handleTransaction(transactionRequest);
    return ResponseEntity.ok("Transaction processed and rewards awarded");
  }
}
