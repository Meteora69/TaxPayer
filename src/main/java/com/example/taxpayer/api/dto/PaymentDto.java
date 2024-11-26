package com.example.taxpayer.api.dto;

public record PaymentDto(
        Long id,
        String paymentDate,
        Double paymentAmount,
        Long userId,
        Long billId
) {}
