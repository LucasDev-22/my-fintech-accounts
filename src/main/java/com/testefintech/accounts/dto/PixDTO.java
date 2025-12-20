package com.testefintech.accounts.dto;

import java.math.BigDecimal;

public record PixDTO(
   Long accountId,
   BigDecimal valor,
   String destino
) {}
