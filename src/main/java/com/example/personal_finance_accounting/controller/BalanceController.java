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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        model.addAttribute("logEntries", readLogFile(userAccount));
        model.addAttribute("userId", userAccount.getUserId());
        model.addAttribute("userAccount", userAccount);
        return "index";
    }


    @PostMapping("/uploadProfilePhoto")
    public String uploadProfilePhoto(@RequestParam("profilePhoto") MultipartFile file, Model model, Authentication authentication) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "redirect:/index"; // Redirect back to profile page if no file is selected
        } else {
            try {
                UserAccount userAccount = userAccountService.findByEmail(authentication.getName());


                Path uploadDir = Paths.get("src/main/resources/static/img");
                String fileName = file.getOriginalFilename();
                Path pathToFile = uploadDir.resolve(fileName);

                File checkFile= new File(String.valueOf(pathToFile));
                if(!checkFile.exists()){

                    Files.createFile(pathToFile);
                    Files.write(pathToFile, file.getBytes());
                }




                // Update the user profile with the new photo path
                userAccount.setPhotoProfile("/static/img/" + fileName);
                userAccountService.updateUserAccount(userAccount.getUserId(), userAccount); // Make sure to save the user account

                model.addAttribute("userAccount", userAccount);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/index";

    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllRecords() {
        balanceService.deleteAllRecords();
        return ResponseEntity.ok("All records have been successfully deleted.");
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userAccountService.deleteUserAccount(id);
        return ResponseEntity.ok("User has been successfully deleted.");
    }


    private List<String> readLogFile(UserAccount user) {
        List<String> logEntries = FileLogger.readLogs(user);
        if(logEntries.size()<=10){
            int startIndex = Math.max(0, logEntries.size() - 10);
            return logEntries.subList(startIndex, logEntries.size());
        }else{
            return logEntries;
        }

    }






}
