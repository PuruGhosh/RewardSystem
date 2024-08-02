package com.assignment.retail_app.Service;

import com.assignment.retail_app.Dto.CustomerRewardResponse;
import com.assignment.retail_app.Entity.Customer;
import com.assignment.retail_app.Entity.Reward;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.CustomerRepository;
import com.assignment.retail_app.Repository.RewardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RewardServiceTest {

  @InjectMocks private RewardService rewardService;

  @Mock private RewardRepository rewardRepository;

  @Mock private CustomerRepository customerRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Test calculate point when purchase less than 50")
  public void testCalculatePoints_LessThan50() {

    BigDecimal amount = BigDecimal.valueOf(30);

    int points = rewardService.calculatePoints(amount);

    assertEquals(0, points);
  }

  @Test
  @DisplayName("Test calculate point when purchase between 50 and 100")
  public void testCalculatePoints_Between50And100() {

    BigDecimal amount = BigDecimal.valueOf(75);

    int points = rewardService.calculatePoints(amount);

    assertEquals(25, points);
  }

  @Test
  @DisplayName("Test calculate point when purchase more then 100")
  public void testCalculatePoints_MoreThan100() {

    BigDecimal amount = BigDecimal.valueOf(150);

    int points = rewardService.calculatePoints(amount);

    assertEquals(150, points);
  }

  @Test
  @DisplayName("Test calculate point")
  public void testAwardPoints() {

    Transaction transaction = new Transaction();
    transaction.setAmount(BigDecimal.valueOf(150));
    transaction.setCustomer(new Customer());

    Reward reward = new Reward();
    reward.setCustomer(transaction.getCustomer());
    reward.setTransaction(transaction);
    reward.setPoints(150);
    reward.setAwardedDate(LocalDateTime.now());

    when(rewardRepository.save(any(Reward.class))).thenReturn(reward);

    rewardService.awardPoints(transaction);

    verify(rewardRepository, times(1)).save(any(Reward.class));
  }

  @Test
  @DisplayName("Test calculate point when customer does not exist")
  public void testGetRewardsByCustomerId_CustomerNotFound() {

    UUID customerId = UUID.randomUUID();
    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    RuntimeException thrown =
        assertThrows(
            RuntimeException.class,
            () -> {
              rewardService.getRewardsByCustomerId(customerId);
            });
    assertEquals("Customer not found", thrown.getMessage());
  }

  @Test
  @DisplayName("Test calculate point when customer does exist")
  public void testGetRewardsByCustomerId_Success() {

    UUID customerId = UUID.randomUUID();
    Customer customer = new Customer();
    customer.setId(customerId);
    customer.setName("xxx");
    customer.setEmail("xxx@yyy.com");
    customer.setPhoneNumber("1234567890");

    Transaction transaction = new Transaction();
    transaction.setId(UUID.randomUUID());
    transaction.setAmount(BigDecimal.valueOf(100));

    Reward reward = new Reward();
    reward.setCustomer(customer);
    reward.setTransaction(transaction);
    reward.setPoints(100);
    reward.setAwardedDate(LocalDateTime.now());

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(rewardRepository.findByCustomerId(customerId))
        .thenReturn(Collections.singletonList(reward));

    CustomerRewardResponse response = rewardService.getRewardsByCustomerId(customerId);

    assertNotNull(response);
    assertEquals(customerId, response.getCustomer().getId());
    assertEquals(100, response.getTotalPoints());
    assertEquals(1, response.getRewards().size());
    assertEquals(100, response.getRewards().get(0).getTransactionAmount().intValue());
  }
}
