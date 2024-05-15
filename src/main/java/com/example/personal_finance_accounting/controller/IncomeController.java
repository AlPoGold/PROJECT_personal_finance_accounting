package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.service.FileLogger;
import com.example.personal_finance_accounting.service.IncomeService;
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
@AllArgsConstructor
@Log
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public String getIncomes(Model model) {
        List<Income> incomes = incomeService.getAllIncomes();
        model.addAttribute("incomes", incomes);
        log.log(Level.INFO, incomes.toString());
        return "incomes";
    }

    @PostMapping
    public String addIncome(@RequestParam("amount") String amount,
                            @RequestParam("source") String source,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        incomeService.addIncome(amount, source, date);
        log.log(Level.INFO, "income was added!");
        FileLogger.log("income was added!" + "|" + "+" +amount);



        return "redirect:/incomes";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteById(id);
        return "redirect:/incomes";
    }

    @GetMapping("/update/{id}")
    public String updateExpense(@PathVariable("id") Long id, Model model){
        Income income = incomeService.findByid(id);
        if(income!=null){
            model.addAttribute("income", income);
        }
        return "redirect:/incomes";
    }


}
