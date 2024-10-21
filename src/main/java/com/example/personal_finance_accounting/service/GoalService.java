package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.model.GoalStatusEnum;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
@Log
public class GoalService {
    private GoalRepository goalRepository;

    private BalanceService balanceService;

    public List<Goal> getUserGoals(UserAccount userAccount) {
        return goalRepository.findByUserAccount(userAccount);
    }


    public Optional<Goal> getGoalById(Long id){
        return goalRepository.findById(id);
    }


    public Goal addGoal(String name, Double targetAmount, LocalDate startDate, LocalDate endDate,
                        GoalStatusEnum status, UserAccount userAccount) {
        if(startDate==null){
            startDate=LocalDate.now();
        }
        if(endDate==null){
            endDate=LocalDate.now();
        }
        Goal goal = new Goal();
        goal.setName(name);
        goal.setTargetAmount(targetAmount);
        goal.setStartDate(startDate);
        goal.setEndDate(endDate);
        goal.setStatus(status);
        goal.setUserAccount(userAccount);

        goalRepository.save(goal);

        return goal;
    }

    /**
     * Adding money to the goal (booked money for goal using its id)
     * @param id goal's id
     * @param amount money's amount for reservation
     *
     */
    @Transactional
    public void increaseMoneyGoalById(Long id, Double amount) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            if(goal.getStatus().name().equals("NOT_STARTED")){
                goal.setStatus(GoalStatusEnum.IN_PROGRESS);
            }
            goal.setCurrentAmount(goal.getCurrentAmount()+amount);
            if(Double.compare(goal.getCurrentAmount(),goal.getTargetAmount())>=0){
                goal.setStatus(GoalStatusEnum.COMPLETED);
            }
            goalRepository.save(goal);
        } else {
            throw new EntityNotFoundException("Goal with id " + id + " not found");
        }



    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);

    }


    public Optional<Goal> findById(Long id) {
        return goalRepository.findById(id);
    }

    @Transactional
    public void updateById(Long id, Goal updatedGoal) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        if (goalOptional.isPresent()) {
            Goal existingGoal = goalOptional.get();
            existingGoal.setName(updatedGoal.getName());
            existingGoal.setTargetAmount(updatedGoal.getTargetAmount());
            existingGoal.setCurrentAmount(updatedGoal.getCurrentAmount());

            LocalDate startDate = updatedGoal.getStartDate();
            LocalDate endDate = updatedGoal.getEndDate();
            if(startDate==null){
                startDate=LocalDate.now();
            }
            if(endDate==null){
                endDate=LocalDate.now();
            }
            existingGoal.setStartDate(startDate);
            existingGoal.setEndDate(endDate);
            existingGoal.setStatus(updatedGoal.getStatus());
            goalRepository.save(existingGoal);
        } else {
            throw new EntityNotFoundException("Goal with id " + id + " not found");
        }

    }
    private static Date convertToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public void initialRepo(UserAccount userAccount) {
        Goal goal = addGoal("exampleGoal", 1000.00,
                LocalDate.now(),
                LocalDate.now(),
                GoalStatusEnum.NOT_STARTED,
                userAccount);

        goalRepository.save(goal);

    }

    public List<Goal> findByUserAccount(UserAccount userAccount) {
        return goalRepository.findByUserAccount(userAccount);
    }
}
