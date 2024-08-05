package com.assignment.retail_app.Service;

import com.assignment.retail_app.Dto.BulkTransactionRequest;
import com.assignment.retail_app.Dto.TransactionRequest;
import com.assignment.retail_app.Entity.Customer;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.CustomerRepository;
import com.assignment.retail_app.Repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {
  @Autowired private TransactionRepository transactionRepository;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private RewardService rewardService;

  /**
   * Create transaction.
   *
   * @param transactionRequest
   */
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

  @Transactional
  public void handleTransaction(Transaction transaction) {
    if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new RuntimeException("Invalid Amount. Amount must be greater than 0.");
    }
    // Save the transaction
    transaction = transactionRepository.save(transaction);

    // Process the reward
    rewardService.awardPoints(transaction);
  }

  /**
   * process multiple transaction for a customer
   * @param bulkTransactionRequest
   * @return
   */
  public List<Transaction> handleBulkTransaction(BulkTransactionRequest bulkTransactionRequest) {
    UUID customerId = bulkTransactionRequest.getCustomerId();
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    List<Transaction> successfulTxns = new ArrayList<>();
    bulkTransactionRequest
        .getTransactions()
        .forEach(
            subTxn -> {
              try {
                LocalDate transactionDate = subTxn.getTransactionDate();
                LocalDateTime transactionDateTime = transactionDate.atTime(10,0);
                Transaction transaction = new Transaction();
                transaction.setCustomer(customer);
                transaction.setAmount(subTxn.getAmount());
                transaction.setTransactionDate(transactionDateTime);
                handleTransaction(transaction);
                successfulTxns.add(transaction);
              } catch (Exception e) {
                log.warn("Transaction Failed {}", subTxn);
              }
            });
    return successfulTxns;
  }
}
