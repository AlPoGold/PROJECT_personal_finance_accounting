package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT g FROM Goal g WHERE g.userAccount = :userAccount")
    List<Goal> findByUserAccount(@Param("userAccount") UserAccount userAccount);

}
