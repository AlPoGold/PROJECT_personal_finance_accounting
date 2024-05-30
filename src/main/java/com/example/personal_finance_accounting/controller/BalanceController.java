package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.BalanceService;
import com.example.personal_finance_accounting.service.FileLogger;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Log
@Controller
@RequestMapping("/index")
public class BalanceController {

    private BalanceService balanceService;
    private UserAccountService userAccountService;

    @GetMapping
    public String getBalance(Model model, Authentication authentication) {
        UserAccount userAccount = userAccountService.findByEmail(authentication.getName());
        Balance balance = balanceService.getUserBalance(userAccount);


        if (balance==null) {
            balance = balanceService.initialBalance(userAccount);
        }

        model.addAttribute("balance", balance);
        model.addAttribute("logEntries", readLogFile());
        return "index";
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllRecords() {
        balanceService.deleteAllRecords();
        return ResponseEntity.ok("All records have been successfully deleted.");
    }


    //TODO: solve error with different count of lines and different users
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
