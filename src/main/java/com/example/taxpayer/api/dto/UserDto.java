package com.example.taxpayer.api.dto;

public record UserDto(
        Long id,
        String name,
        String email,
        Double balance
) {}
