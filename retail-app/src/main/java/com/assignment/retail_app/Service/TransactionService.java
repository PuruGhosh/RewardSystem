package com.assignment.retail_app.Service;

import com.assignment.retail_app.Dto.TransactionRequest;
import com.assignment.retail_app.Entity.Customer;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.CustomerRepository;
import com.assignment.retail_app.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
  @Autowired private TransactionRepository transactionRepository;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private RewardService rewardService;

  @Transactional
  public void handleTransaction(TransactionRequest transactionRequest) {

    if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Invalid Amount. Amount must be greater than 0.");
    }
    Customer customer =
        customerRepository
            .findById(transactionRequest.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    Transaction transaction = new Transaction();
    transaction.setCustomer(customer);
    transaction.setAmount(transactionRequest.getAmount());
    transaction.setTransactionDate(LocalDateTime.now());

    // Save the transaction
    transaction = transactionRepository.save(transaction);

    // Process the reward
    rewardService.awardPoints(transaction);
  }
}
