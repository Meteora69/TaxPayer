package com.example.taxpayer.api.dto;

import com.example.taxpayer.api.enums.UtilityService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
public record UtilityServiceCreationDto(
        @NotNull(message = "Service name cannot be null.")
        //@UniqueServiceName // Перевірка на унікальність імені
        UtilityService name,

        @NotNull(message = "Fixed status must be provided.")
        Boolean isFixed,

        @NotNull(message = "Tariff cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Tariff must be greater than 0.")
        BigDecimal tariff,

        @NotBlank(message = "Cycle cannot be blank.")
        String cycle
) {}
