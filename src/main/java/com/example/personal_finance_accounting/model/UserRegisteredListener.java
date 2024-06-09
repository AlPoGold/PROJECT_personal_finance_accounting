package com.example.personal_finance_accounting.model;

import com.example.personal_finance_accounting.service.BalanceService;
import com.example.personal_finance_accounting.service.ExpenseService;
import com.example.personal_finance_accounting.service.GoalService;
import com.example.personal_finance_accounting.service.IncomeService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationListener;

import java.util.logging.Level;

@AllArgsConstructor
@Log
public class UserRegisteredListener implements ApplicationListener<UserRegisteredEvent> {

    private final BalanceService balanceService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final GoalService goalService;
    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        UserAccount userAccount = event.getUserAccount();
        expenseService.initialRepo(userAccount);
        log.log(Level.INFO, "create expense repo");
        incomeService.initialRepo(userAccount);
        log.log(Level.INFO, "create income repo");
        goalService.initialRepo(userAccount);
        log.log(Level.INFO, "create goal repo");
        balanceService.addBalance(userAccount);
        log.log(Level.INFO, "create balance repo"+ balanceService.getUserBalance(userAccount));

    }
}
