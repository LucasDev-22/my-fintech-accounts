package com.testefintech.accounts.dto;

import java.math.BigDecimal;

public record PixRequest(
        BigDecimal valor,
        String destino
) {}