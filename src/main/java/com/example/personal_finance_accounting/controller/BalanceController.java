package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.service.BalanceService;
import com.example.personal_finance_accounting.service.FileLogger;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Controller
@RequestMapping("/index")
public class BalanceController {

    @Autowired
    private BalanceService service;

    @GetMapping
    public String getBalance(Model model) {
        Balance balance  = service.calculateBalance();
        model.addAttribute("balance", balance);
        model.addAttribute("logEntries", readLogFile());
        return "index";
    }

    private List<String> readLogFile() {
        List<String> logEntries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FileLogger.FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int startIndex = Math.max(0, logEntries.size() - 10);
        return logEntries.subList(startIndex, logEntries.size());
    }


}
