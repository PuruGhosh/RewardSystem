package com.assignment.retail_app.Repository;

import com.assignment.retail_app.Entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {

  List<Reward> findByCustomerId(UUID customerId);

  @Query("SELECT r FROM Reward r WHERE r.customer.id = :customerId AND r.awardedDate >= :monthsAgo")
  List<Reward> findByCustomerIdAndAwardedDateAfter(
      @Param("customerId") UUID customerId, @Param("monthsAgo") LocalDateTime threeMonthsAgo);
}
