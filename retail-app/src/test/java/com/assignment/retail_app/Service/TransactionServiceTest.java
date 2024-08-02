package com.assignment.retail_app.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.assignment.retail_app.Dto.TransactionRequest;
import com.assignment.retail_app.Entity.Customer;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.CustomerRepository;
import com.assignment.retail_app.Repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionServiceTest {

  @InjectMocks private TransactionService transactionService;

  @Mock private TransactionRepository transactionRepository;

  @Mock private CustomerRepository customerRepository;

  @Mock private RewardService rewardService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Handle Transaction valid amount")
  public void testHandleTransaction_Success() {
    // Arrange
    UUID customerId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);
    TransactionRequest request = new TransactionRequest();
    request.setCustomerId(customerId);
    request.setAmount(amount);

    Customer customer = new Customer();
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setCustomer(customer);

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    // Act
    transactionService.handleTransaction(request);

    // Assert
    verify(transactionRepository, times(1)).save(any(Transaction.class));
    verify(rewardService, times(1)).awardPoints(transaction);
  }

  @Test
  @DisplayName("Handle transaction invalid amount")
  public void testHandleTransaction_InvalidAmount() {
    // Arrange
    UUID customerId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(-50);
    TransactionRequest request = new TransactionRequest();
    request.setCustomerId(customerId);
    request.setAmount(amount);

    // Act & Assert
    RuntimeException thrown =
        assertThrows(
            RuntimeException.class,
            () -> {
              transactionService.handleTransaction(request);
            });
    assertEquals("Invalid Amount. Amount must be greater than 0.", thrown.getMessage());
  }

  @Test
  @DisplayName("Handle transaction customer not found")
  public void testHandleTransaction_CustomerNotFound() {
    // Arrange
    UUID customerId = UUID.randomUUID();
    BigDecimal amount = BigDecimal.valueOf(100);
    TransactionRequest request = new TransactionRequest();
    request.setCustomerId(customerId);
    request.setAmount(amount);

    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    // Act & Assert
    RuntimeException thrown =
        assertThrows(
            RuntimeException.class,
            () -> {
              transactionService.handleTransaction(request);
            });
    assertEquals("Customer not found", thrown.getMessage());
  }
}
