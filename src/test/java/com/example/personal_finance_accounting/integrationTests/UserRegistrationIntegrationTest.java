package com.example.personal_finance_accounting.integrationTests;

import com.example.personal_finance_accounting.model.Balance;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.BalanceRepository;
import com.example.personal_finance_accounting.repository.ExpenseRepository;
import com.example.personal_finance_accounting.repository.GoalRepository;
import com.example.personal_finance_accounting.repository.IncomeRepository;
import com.example.personal_finance_accounting.service.UserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRegistrationIntegrationTest {
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private GoalRepository goalRepository;

    @Test
    public void testUserRegistrationCreatesInitialData() {
        UserAccount newUser = new UserAccount();
        newUser.setUsername("testuser");
        newUser.setPassword("password");

        userAccountService.createUserAccount(newUser);

        UserAccount savedUser = userAccountService.findByEmail("testuser");
        assertNotNull(savedUser);

        Balance balance = balanceRepository.findByUserAccount(savedUser);
        assertNotNull(balance);
        assertEquals(0.0, balance.getTotalBalance());


        // Другие проверки для начальных данных
    }
}
