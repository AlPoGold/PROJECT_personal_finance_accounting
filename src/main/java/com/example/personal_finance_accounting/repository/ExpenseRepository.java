package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.Expense;
import com.example.personal_finance_accounting.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    BigDecimal calculateTotalExpense();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalExpenseByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("UPDATE Expense e SET e.amount = :amount, e.category = :category, e.date = :date WHERE e.id = :id")
    void updateById(Long id, BigDecimal amount, String category, Date date);

    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    List<Expense> findExpensesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT e FROM Expense e WHERE e.userAccount = :userAccount")
    List<Expense> findByUserAccount(@Param("userAccount") UserAccount userAccount);
}
