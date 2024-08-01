package com.assignment.retail_app.Dto;


import lombok.Data;

import java.util.List;

@Data
public class CustomerRewardResponse {
    private CustomerResponse customer;
    private int totalPoints;
    private List<RewardResponse> Rewards;
}
