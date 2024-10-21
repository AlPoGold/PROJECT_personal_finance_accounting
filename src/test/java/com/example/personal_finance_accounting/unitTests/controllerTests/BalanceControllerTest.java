package com.example.personal_finance_accounting.unitTests.controllerTests;

import com.example.personal_finance_accounting.controller.BalanceController;
import com.example.personal_finance_accounting.service.BalanceService;
import com.example.personal_finance_accounting.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BalanceControllerTest {
    @Mock
    private BalanceService balanceService;

    @Mock
    private UserAccountService userAccountService;

    @InjectMocks
    private BalanceController balanceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        ResponseEntity<String> responseEntity = balanceController.deleteUser(userId);

        verify(userAccountService, times(1)).deleteUserAccount(userId);
        assertEquals("User has been successfully deleted.", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testDeleteAllRecords() {
        ResponseEntity<String> responseEntity = balanceController.deleteAllRecords();

        verify(balanceService, times(1)).deleteAllRecords();
        assertEquals("All records have been successfully deleted.", responseEntity.getBody());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
