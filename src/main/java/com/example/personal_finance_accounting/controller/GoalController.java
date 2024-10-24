package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.model.GoalStatusEnum;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.FileLogger;
import com.example.personal_finance_accounting.service.GoalService;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Controller
@Data
@Log
@AllArgsConstructor
public class GoalController {
    private GoalService goalService;
    private UserAccountService userAccountService;

    /**
     * Show html-page with goals
     *
     * @param model Model for thymeleaf template
     * @return Name of page
     */
    @GetMapping("/goals")
    public String getGoals(Model model, Authentication authentication){
        UserAccount userAccount = userAccountService.findByEmail(authentication.getName());
        List<Goal> goals = goalService.getUserGoals(userAccount);
        model.addAttribute("listGoals", goals);
        model.addAttribute("userId", userAccount.getUserId());
        return "goals";
    }

    /**
     * Adding new goal
     *
     * @param name Description of goal
     * @param targetAmount need money's amount for goal
     * @param startDate when goal starts
     * @param endDate when goal ends
     * @param status status of goal
     * @param auth user's session
     * @return redirect to the html-page with goals
     */
    @PostMapping("/goals")
    public String addGoal(@RequestParam("name") String name,
                          @RequestParam("targetAmount") Double targetAmount,
                          @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                          @RequestParam("status")GoalStatusEnum status,
                          Authentication auth){
        UserAccount userAccount = userAccountService.findByEmail(auth.getName());
        goalService.addGoal(name, targetAmount, startDate, endDate, status, userAccount);
        FileLogger.log(userAccount, "adding new goal!|"+ targetAmount.toString());
        log.log(Level.INFO, "goal was added!");


        return "redirect:/goals";
    }

    /** Redirect money from money's account to the goal by its id
     *
     * @param id Goal's id
     * @param amount  money's amount for goal
     * @return server's response
     */
    @PutMapping("/goals/{id}")
    public ResponseEntity<Goal> sendMoneyToGoal(@PathVariable Long id, @RequestParam("amount") Double amount){
        log.log(Level.INFO, amount + " was sent to your goal!");
        goalService.increaseMoneyGoalById(id, amount);
        Goal goal = goalService.getGoalById(id).orElse(null);
        return ResponseEntity.ok(goal);
    }

    /**
     * Update existing goal
     *
     * @param id Goal's id
     * @param updatedGoal Updated goal with new values
     * @return redirect to the html-page with goals
     */
    @PutMapping("/goals/update/{id}")
    public ResponseEntity<String> updateGoal(@PathVariable("id") Long id, @RequestBody Goal updatedGoal) {
        Optional<Goal> existingGoalOptional = goalService.findById(id);
        if (existingGoalOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        goalService.updateById(id, updatedGoal);
        return ResponseEntity.ok("Goal updated successfully");
    }

    /**
     * Delete goal using id
     *
     * @param id Goal's id
     * @return redirect to the html-page with goals
     */
    @GetMapping("goals/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return "redirect:/goals";
    }

}
