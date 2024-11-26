package com.example.taxpayer.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressCreationDto(
        @NotBlank(message = "Street cannot be blank.")
        String street,

        @NotBlank(message = "House number cannot be blank.")
        String houseNumber,

        @NotBlank(message = "City cannot be blank.")
        String city,

        @NotBlank(message = "ZIP code cannot be blank.")
        String zip
) {}
