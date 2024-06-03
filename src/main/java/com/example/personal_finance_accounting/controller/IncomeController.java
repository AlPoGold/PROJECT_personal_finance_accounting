package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.model.Income;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.FileLogger;
import com.example.personal_finance_accounting.service.GoalService;
import com.example.personal_finance_accounting.service.IncomeService;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Controller
@AllArgsConstructor
@Log
@RequestMapping("/incomes")
public class IncomeController {
    private GoalService goalService;
    private IncomeService incomeService;
    private UserAccountService userAccountService;



    @GetMapping
    public String getIncomes(Model model, Authentication authentication) {

        UserAccount userAccount = userAccountService.findByEmail(authentication.getName());
        List<Income> incomes = incomeService.getUserIncomes(userAccount);

        incomes.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        BigDecimal totalIncomes = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Goal> goals = goalService.findByUserAccount(userAccount);

        Double totalReservations = goals.stream().mapToDouble(Goal::getCurrentAmount).sum();
        model.addAttribute("incomes", incomes);
        model.addAttribute("totalIncomes", totalIncomes);
        model.addAttribute("totalReservations", totalReservations);
        model.addAttribute("userId", userAccount.getUserId());
        return "incomes";
    }

    @PostMapping
    public String addIncome(@RequestParam("amount") String amount,
                            @RequestParam("source") String source,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Authentication auth){
        UserAccount userAccount = userAccountService.findByEmail(auth.getName());
        incomeService.addIncome(amount, source, date, userAccount);
        log.log(Level.INFO, "income was added!");
        FileLogger.log(userAccount, "income was added!" + "|" + "+" +amount);



        return "redirect:/incomes";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable("id") Long id) {
        incomeService.deleteById(id);
        return "redirect:/incomes";
    }

    @GetMapping("/update/{id}")
    public String updateIncome(@PathVariable("id") Long id, Model model){
        incomeService.findById(id).ifPresent(income -> model.addAttribute("income", income));
        return "redirect:/incomes/update/{id}";
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIncome(@PathVariable("id") Long id, @RequestBody Income updatedIncome, Authentication auth) {
        // Проверяем, существует ли запись с указанным id
        UserAccount user = userAccountService.findByEmail(auth.getName());
        Optional<Income> existingIncomeOptional = incomeService.findById(id);
        if (existingIncomeOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        incomeService.updateById(id, updatedIncome, user);

        return ResponseEntity.ok("Income updated successfully");
    }


}
