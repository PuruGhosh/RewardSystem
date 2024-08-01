package com.assignment.retail_app.Service;

import com.assignment.retail_app.Entity.Reward;
import com.assignment.retail_app.Entity.Transaction;
import com.assignment.retail_app.Repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    public int calculatePoints(BigDecimal amount) {
        int points = 0;
        if (amount.compareTo(new BigDecimal(100)) > 0) {
            points += (amount.subtract(new BigDecimal(100)).intValue() * 2);
            amount = new BigDecimal(100);
        }
        if (amount.compareTo(new BigDecimal(50)) > 0) {
            points += (amount.subtract(new BigDecimal(50)).intValue());
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
}
