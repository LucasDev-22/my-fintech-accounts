package com.testefintech.accounts.dto;

import java.time.LocalDate;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String cpf,
        LocalDate birthDate,
        String documentFront,
        String documentBack
) {}