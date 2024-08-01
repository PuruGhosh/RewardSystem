package com.assignment.retail_app.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Transaction transaction;
    private int points;
    private LocalDateTime awardedDate;

}
