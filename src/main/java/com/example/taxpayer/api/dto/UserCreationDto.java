package com.example.taxpayer.api.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserCreationDto(
        @NotBlank(message = "Name cannot be blank.") String name,

        @Email(message = "Email should be valid.") @NotBlank(message = "Email cannot be blank.") String email,

        @NotBlank(message = "Password cannot be blank.") String password,

        @NotNull(message = "Balance cannot be null.") @Positive(message = "Balance must be positive.") Double balance
) {}
