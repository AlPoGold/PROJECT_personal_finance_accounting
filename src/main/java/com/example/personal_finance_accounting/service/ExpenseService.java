package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }
}
