package com.shubham.saas.core.dto;

import java.math.BigDecimal;

public record TrialBalanceDto(
        Long accountId,
        String accountName,
        BigDecimal totalDebit,
        BigDecimal totalCredit
) {
}
