package com.example.personal_finance_accounting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    User user;
    BigDecimal currentBalance;
}
