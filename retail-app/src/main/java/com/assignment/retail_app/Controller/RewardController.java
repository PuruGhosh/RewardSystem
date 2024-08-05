package com.assignment.retail_app.Controller;

import com.assignment.retail_app.Dto.CustomerRewardResponse;
import com.assignment.retail_app.Service.RewardService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reward")
public class RewardController {

  @Autowired private RewardService rewardService;

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<CustomerRewardResponse> getRewardsByCustomer(
      @PathVariable UUID customerId, @RequestParam(value = "months", defaultValue = "3") int months) {

    if (customerId == null) {
      throw new RuntimeException("Customer id is null.");
    }
    CustomerRewardResponse response = rewardService.getRewardsByCustomerId(customerId, months);
    return ResponseEntity.ok(response);
  }
}
