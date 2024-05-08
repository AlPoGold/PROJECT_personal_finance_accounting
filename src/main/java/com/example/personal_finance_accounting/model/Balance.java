package com.example.personal_finance_accounting.model;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class Balance {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalBalance;
}
