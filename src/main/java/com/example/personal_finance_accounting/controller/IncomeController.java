package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.service.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class IncomeController {

    private IncomeService incomeService;

    @GetMapping("/incomes")
    public String getIncomes(Model model) {
        List<Income> incomes = incomeService.getAllIncomes();
        model.addAttribute("incomes", incomes);
        return "incomes";
    }


}
