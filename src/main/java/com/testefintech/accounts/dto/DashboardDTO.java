package com.testefintech.accounts.dto;

import com.testefintech.accounts.model.Transaction;
import java.math.BigDecimal;
import java.util.List;

public record DashboardDTO(
        BigDecimal balance,
        List<Transaction> transactions
) {}