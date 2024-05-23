package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i")
    BigDecimal calculateTotalIncome();

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalIncomeByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("UPDATE Income i SET i.amount = :amount, i.source = :source, i.date = :date WHERE i.id = :id")
    void updateById(Long id, BigDecimal amount, String source, Date date);

    @Query("SELECT i FROM Income i WHERE i.date BETWEEN :startDate AND :endDate")
    List<Income> findIncomesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
