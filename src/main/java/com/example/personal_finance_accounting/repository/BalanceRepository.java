package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceRepository extends JpaRepository<Balance, Long> {


    @Query("SELECT b FROM Balance b WHERE b.userAccount = :userAccount")
    Balance findByUserAccount(@Param("userAccount") UserAccount userAccount);
}
