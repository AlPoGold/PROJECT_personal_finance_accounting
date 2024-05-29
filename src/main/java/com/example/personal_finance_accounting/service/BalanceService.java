package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import com.example.personal_finance_accounting.repository.GoalRepository;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class BalanceService {
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final GoalRepository goalRepository;

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

    public Balance calculateBalanceForLastMonth() {
        Date startDate = getDateMonthsAgo(1);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRange(startDate, endDate);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRange(startDate, endDate);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public Balance calculateBalanceForLast3Months() {
        Date startDate = getDateMonthsAgo(3);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRange(startDate, endDate);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRange(startDate, endDate);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public Balance calculateBalanceForLastYear() {
        Date startDate = getDateYearsAgo(1);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRange(startDate, endDate);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRange(startDate, endDate);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    private Date getDateMonthsAgo(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }

    private Date getDateYearsAgo(int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -years);
        return calendar.getTime();
    }

    public void deleteAllRecords() {
        incomeRepository.deleteAll();
        expenseRepository.deleteAll();
        goalRepository.deleteAll();

    }
}
