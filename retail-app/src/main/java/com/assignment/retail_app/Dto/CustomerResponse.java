package com.assignment.retail_app.Dto;

import lombok.Data;

import java.util.UUID;


@Data
public class CustomerResponse {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
}
