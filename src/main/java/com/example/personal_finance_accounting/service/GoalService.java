package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Log
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

    @Transactional
    public void increaseMoneyGoalById(Long id, Double amount) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            goal.setCurrentAmount(goal.getCurrentAmount()+amount);
            goalRepository.save(goal);
        } else {
            throw new EntityNotFoundException("Goal with id " + id + " not found");
        }



    }
}
