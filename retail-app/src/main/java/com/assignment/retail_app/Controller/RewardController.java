package com.assignment.retail_app.Controller;

import com.assignment.retail_app.Dto.CustomerRewardResponse;
import com.assignment.retail_app.Service.RewardService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reward")
public class RewardController {

  @Autowired private RewardService rewardService;

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<CustomerRewardResponse> getRewardsByCustomer(
      @PathVariable UUID customerId) {

    if (customerId == null) {
      throw new RuntimeException("Customer id is null.");
    }
    CustomerRewardResponse response = rewardService.getRewardsByCustomerId(customerId);
    return ResponseEntity.ok(response);
  }
}
