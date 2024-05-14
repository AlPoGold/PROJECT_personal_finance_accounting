package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.repository.GoalRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private BalanceService balanceService;

    public Optional<Goal> getGoalById(Long id){
        return goalRepository.findById(id);
    }

    public List<Goal> getAllGoals(){
        return goalRepository.findAll();
    }

    public void addGoal(String name, Double targetAmount, LocalDate startDate, LocalDate endDate) {
        Goal goal = new Goal();
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setStartDate(startDate);
        goal.setEndDate(endDate);

        goalRepository.save(goal);


    }
}
