package com.assignment.retail_app.Service;

import com.assignment.retail_app.Dto.CustomerResponse;
import com.assignment.retail_app.Dto.CustomerRewardResponse;
import com.assignment.retail_app.Dto.RewardResponse;
import com.assignment.retail_app.Entity.Customer;
import com.assignment.retail_app.Entity.Reward;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.CustomerRepository;
import com.assignment.retail_app.Repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RewardService {

  @Autowired private RewardRepository rewardRepository;
  @Autowired private CustomerRepository customerRepository;

  public int calculatePoints(BigDecimal amount) {
    int points = 0;
    int amountInt = amount.intValue(); // Discard the decimal part

    if (amountInt > 100) {
      points += (amountInt - 100) * 2;
      amountInt = 100;
    }
    if (amountInt > 50) {
      points += (amountInt - 50);
    }
    return points;
  }

  public void awardPoints(Transaction transaction) {
    int points = calculatePoints(transaction.getAmount());
    Reward reward = new Reward();
    reward.setCustomer(transaction.getCustomer());
    reward.setTransaction(transaction);
    reward.setPoints(points);
    reward.setAwardedDate(LocalDateTime.now());
    rewardRepository.save(reward);
  }

  public CustomerRewardResponse getRewardsByCustomerId(UUID customerId) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    List<Reward> rewards = rewardRepository.findByCustomerId(customerId);
    int totalPoints =
        (rewards != null && !rewards.isEmpty())
            ? rewards.stream().mapToInt(Reward::getPoints).sum()
            : 0;

    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(customer.getId());
    customerResponse.setName(customer.getName());
    customerResponse.setEmail(customer.getEmail());
    customerResponse.setPhoneNumber(customer.getPhoneNumber());

    List<RewardResponse> rewardResponses =
        (rewards != null)
            ? rewards.stream()
                .map(
                    reward -> {
                      RewardResponse rewardResponse = new RewardResponse();
                      rewardResponse.setTransactionId(reward.getTransaction().getId());
                      rewardResponse.setTransactionAmount(reward.getTransaction().getAmount());
                      rewardResponse.setPoints(reward.getPoints());
                      rewardResponse.setAwardedDate(reward.getAwardedDate());
                      return rewardResponse;
                    })
                .collect(Collectors.toList())
            : Collections.emptyList();

    CustomerRewardResponse response = new CustomerRewardResponse();
    response.setCustomer(customerResponse);
    response.setTotalPoints(totalPoints);
    response.setRewards(rewardResponses);

    return response;
  }
}
