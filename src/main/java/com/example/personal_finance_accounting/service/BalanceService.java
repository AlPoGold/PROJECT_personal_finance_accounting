package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.User;
import com.example.personal_finance_accounting.model.exceptions.NotUserFoundException;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import com.example.personal_finance_accounting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BalanceService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Balance calculateBalance() {
        BigDecimal totalIncome = incomeRepository.calculateTotalIncome();
        BigDecimal totalExpense = expenseRepository.calculateTotalExpense();
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public void saveBalanceForUser(String login) {
        User user = userRepository.findByUserLogin(login);
        if (user != null) {
            Balance balance = calculateBalance();
            user.setCurrentBalance(balance);
            userRepository.save(user);
        } else {
            throw new NotUserFoundException("User is not found!");
        }
    }
}
