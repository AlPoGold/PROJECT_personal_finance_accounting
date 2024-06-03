package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.BalanceRepository;
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
    private final BalanceRepository balanceRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final GoalRepository goalRepository;



    public Balance getUserBalance(UserAccount userAccount) {
        Balance balance = balanceRepository.findByUserAccount(userAccount);
        if(balance!=null){
            updateBalance(balance, userAccount);
        }

        return balance;
    }

    private void updateBalance(Balance balance, UserAccount user) {
        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeUser(user);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseUser(user);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);
    }

    public void addBalance(UserAccount userAccount) {
        Balance balance = calculateBalance(userAccount);
        balance.setUserAccount(userAccount);
        balanceRepository.save(balance);
    }

    public Balance calculateBalance(UserAccount user) {
        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeUser(user);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseUser(user);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public Balance initialBalance(UserAccount userAccount){
        Balance balance = new Balance();
        balance.setTotalIncome(BigDecimal.ZERO);
        balance.setTotalExpense(BigDecimal.ZERO);
        balance.setTotalBalance(BigDecimal.ZERO);
        balance.setUserAccount(userAccount);
        balanceRepository.save(balance);

        return balance;

    }

    public Balance calculateBalanceForLastMonth(UserAccount user) {
        Date startDate = getDateMonthsAgo(1);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRangeUser(startDate, endDate, user);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRangeUser(startDate, endDate, user);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public Balance calculateBalanceForLast3Months(UserAccount user) {
        Date startDate = getDateMonthsAgo(3);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRangeUser(startDate, endDate, user);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRangeUser(startDate, endDate, user);
        BigDecimal totalBalance = totalIncome.subtract(totalExpense);

        Balance balance = new Balance();
        balance.setTotalIncome(totalIncome);
        balance.setTotalExpense(totalExpense);
        balance.setTotalBalance(totalBalance);

        return balance;
    }

    public Balance calculateBalanceForLastYear(UserAccount user) {
        Date startDate = getDateYearsAgo(1);
        Date endDate = new Date();

        BigDecimal totalIncome = incomeRepository.calculateTotalIncomeByDateRangeUser(startDate, endDate, user);
        BigDecimal totalExpense = expenseRepository.calculateTotalExpenseByDateRangeUser(startDate, endDate, user);
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
        balanceRepository.deleteAll();

    }
}
