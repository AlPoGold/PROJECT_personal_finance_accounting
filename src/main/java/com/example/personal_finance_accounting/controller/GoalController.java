package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.service.GoalService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

@Controller
@Data
@Log
@AllArgsConstructor
public class GoalController {
    private GoalService goalService;

    @GetMapping("/goals")
    public String getGoals(Model model){
        List<Goal> goals = goalService.getAllGoals();
        model.addAttribute("listGoals", goals);
        return "goals";
    }

    @PostMapping("/goals")
    public String addGoal(@RequestParam("name") String name,
                          @RequestParam("targetAmount") Double targetAmount,
                          @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        goalService.addGoal(name, targetAmount, startDate, endDate);
        log.log(Level.INFO, "goal was added!");


        return "redirect:/goals";
    }

}
