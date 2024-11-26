package com.example.taxpayer.api.dto;

public record AddressDto(
        Long id,
        String street,
        String houseNumber,
        String city,
        String zip
) {}
