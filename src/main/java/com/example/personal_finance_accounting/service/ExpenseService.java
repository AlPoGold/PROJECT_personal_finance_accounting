package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public List<Expense> getUserExpenses(UserAccount userAccount) {
        return expenseRepository.findByUserAccount(userAccount);
    }


    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense addExpense(String amount, String category, Date date, UserAccount userAccount) {
        Expense newExpense = new Expense();
        newExpense.setAmount(BigDecimal.valueOf(Long.parseLong(amount)));
        newExpense.setCategory(category);
        newExpense.setDate(date);
        newExpense.setUserAccount(userAccount);
        return expenseRepository.save(newExpense);


    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public void updateById(Long id, Expense updatedExpense) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);
        if (expenseOptional.isPresent()) {
            Expense expense = expenseOptional.get();
            expense.setAmount(updatedExpense.getAmount());
            expense.setCategory(updatedExpense.getCategory());
            expense.setDate(updatedExpense.getDate());
            expenseRepository.save(expense);
        } else {
            throw new EntityNotFoundException("Expense with id " + id + " not found");
        }
    }

    public List<Expense> getAllExpensesForLast3Months() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpensesByDateRange(threeMonthsAgo, today);
    }

    public List<Expense> getAllExpensesForLastYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date oneYearAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpensesByDateRange(oneYearAgo, today);
    }

    public List<Expense> getAllExpensesForLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();
        Date today = new Date();
        return expenseRepository.findExpensesByDateRange(oneMonthAgo, today);
    }

    public void initialRepo(UserAccount userAccount) {
        Expense expense = addExpense("1000", "example", convertToDate(LocalDate.now()), userAccount);

    }


    private static Date convertToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
