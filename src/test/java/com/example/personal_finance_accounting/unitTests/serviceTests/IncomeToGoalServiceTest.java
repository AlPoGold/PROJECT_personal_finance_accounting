package com.example.personal_finance_accounting.unitTests.serviceTests;

import com.example.personal_finance_accounting.model.Goal;
import com.example.personal_finance_accounting.model.GoalStatusEnum;
import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.GoalRepository;
import com.example.personal_finance_accounting.service.BalanceService;
import com.example.personal_finance_accounting.service.GoalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IncomeToGoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private BalanceService balanceService;

    @InjectMocks
    private GoalService goalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Increase goal adding money")
    void testIncreaseMoneyGoalById() {
        UserAccount userAccount = new UserAccount();
        Goal goal = new Goal();
        goal.setId(1L);
        goal.setStatus(GoalStatusEnum.NOT_STARTED);
        goal.setTargetAmount(1000.00);
        goal.setCurrentAmount(500.00);

        when(goalRepository.findById(anyLong())).thenReturn(Optional.of(goal));

        goalService.increaseMoneyGoalById(1L, 600.00);

        assert(goal.getCurrentAmount() == 1100.00);

        assert(goal.getStatus() == GoalStatusEnum.COMPLETED);

        verify(goalRepository, times(1)).save(goal);
    }

}
