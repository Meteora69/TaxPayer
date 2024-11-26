package com.example.taxpayer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentCreationDto(
        @NotNull(message = "Payment date cannot be null.") String paymentDate,
        @NotNull(message = "Payment amount cannot be null.")
        @Positive(message = "Payment amount must be positive.") Double paymentAmount,
        @NotNull(message = "User ID cannot be null.") Long userId,
        @NotNull(message = "Bill ID cannot be null.") Long billId
) {}
