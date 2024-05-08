package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense addExpense(String amount, String category, Date date) {
        Expense newExpense = new Expense();
        newExpense.setAmount(BigDecimal.valueOf(Long.parseLong(amount)));
        newExpense.setCategory(category);
        newExpense.setDate(date);
        return expenseRepository.save(newExpense);


    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense findByid(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }
}
