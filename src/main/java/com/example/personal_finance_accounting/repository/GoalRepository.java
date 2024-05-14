package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
