package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@Controller
@RequestMapping("/")
public class BalanceController {
    @Autowired
    private BalanceService service;

    @GetMapping
    public String getBalance(Model model) {
        Balance balance  = service.calculateBalance();
        model.addAttribute("balance", balance);
        return "index";
    }


}
