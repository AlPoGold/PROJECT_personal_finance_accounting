package com.example.personal_finance_accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="balances")
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balanceId;

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalBalance;
}
