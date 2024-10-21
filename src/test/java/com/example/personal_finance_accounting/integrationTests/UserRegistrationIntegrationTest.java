package com.example.personal_finance_accounting.integrationTests;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.UserAccountRepository;
import com.example.personal_finance_accounting.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationIntegrationTest {
    @InjectMocks
    private UserAccountService userAccountService;
    @Mock
    private UserAccountRepository userRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testUserRegistrationCreatesInitialData() {

        UserAccount newUser = new UserAccount();
        newUser.setUserId(1L);
        newUser.setEmail("test@user");
        newUser.setPassword(encoder().encode("password"));
        newUser.setUsername("test");
        newUser.setCity("testCity");
        newUser.setBirthDate(new Date(1991 - 1900, Calendar.MARCH, 21));
        userAccountService.createUserAccount(newUser);
        when(userRepository.findByEmail("test@user")).thenReturn(Optional.of(newUser));
        UserAccount foundUser = userAccountService.findByEmail("test@user");
        assertEquals(newUser.getEmail(), foundUser.getEmail());
    }
}
