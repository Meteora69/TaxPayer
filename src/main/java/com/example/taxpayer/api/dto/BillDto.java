package com.example.taxpayer.api.dto;


public record BillDto(
        Long id,
        Double amount,
        String dueDate,
        boolean isPayed,
        String description,
        Long userId,
        Long addressId,
        Long serviceId
) {}
