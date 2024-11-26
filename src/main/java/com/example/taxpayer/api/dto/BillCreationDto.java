package com.example.taxpayer.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BillCreationDto(
        @NotNull(message = "Amount cannot be null.")
        @Positive(message = "Amount must be positive.") Double amount,

        @NotBlank(message = "Due date cannot be blank.") String dueDate,

        @NotBlank(message = "Description cannot be blank.") String description,

        @NotNull(message = "User ID cannot be null.") Long userId,

        @NotNull(message = "Address ID cannot be null.") Long addressId,

        @NotNull(message = "Service ID cannot be null.") Long serviceId
) {}
