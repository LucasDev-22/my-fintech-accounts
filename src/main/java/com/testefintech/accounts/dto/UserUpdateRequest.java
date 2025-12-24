package com.testefintech.accounts.dto;

public record UserUpdateRequest(
        String name,
        String phone,
        String profileImage
) {}