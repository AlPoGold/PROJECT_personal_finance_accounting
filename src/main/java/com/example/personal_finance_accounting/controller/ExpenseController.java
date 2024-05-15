package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.service.ExpenseService;
import com.example.personal_finance_accounting.service.FileLogger;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Controller
@RequestMapping("/expenses")
@AllArgsConstructor
@Log
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public String getExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "expenses";
    }

    @PostMapping
    public String addExpense(@RequestParam("amount") String amount,
                            @RequestParam("category") String category,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        expenseService.addExpense(amount, category, date);
        log.log(Level.INFO, "expense was added!");
        FileLogger.log("expense was added!" + "|" + "-" +amount);


        return "redirect:/expenses";
    }


    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteById(id);
        return "redirect:/expenses";
    }

    @GetMapping("/update/{id}")
    public String updateExpense(@PathVariable("id") Long id, Model model){
        Expense expense = expenseService.findByid(id);
        if(expense!=null){
            model.addAttribute("expense", expense);
        }
        return "redirect:/expenses";
    }


}
