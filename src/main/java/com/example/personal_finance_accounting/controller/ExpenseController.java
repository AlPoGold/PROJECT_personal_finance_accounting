package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public String getExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "expenses";
    }


}
