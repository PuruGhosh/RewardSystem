package com.assignment.retail_app.Repository;

import com.assignment.retail_app.Entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RewardRepository extends JpaRepository<Reward, UUID> {

}
