package com.example.taxpayer.api.dto;

import com.example.taxpayer.api.enums.UtilityService;

import java.math.BigDecimal;

public record UtilityServiceDto(
        Long id,
        UtilityService name,
        Boolean isFixed,
        BigDecimal tariff,
        String cycle
) {}