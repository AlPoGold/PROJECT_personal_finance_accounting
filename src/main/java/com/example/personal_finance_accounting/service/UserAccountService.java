package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.model.UserRegisteredEvent;
import com.example.personal_finance_accounting.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@AllArgsConstructor
public class UserAccountService {


    private UserAccountRepository userRepository;
    private ApplicationEventPublisher eventPublisher;
    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    // Create a new user account
    public UserAccount createUserAccount(UserAccount userAccount) {
        userAccount.setPassword(encoder().encode(userAccount.getPassword()));
        UserAccount user =  userRepository.save(userAccount);
        eventPublisher.publishEvent(new UserRegisteredEvent(this, user));
        return user;
    }

    // Get all user accounts
    public List<UserAccount> getAllUserAccounts() {
        return userRepository.findAll();
    }

    // Get a user account by ID
    public UserAccount getUserAccountById(Long id) {
        Optional<UserAccount> userAccount = userRepository.findById(id);
        return userAccount.orElseThrow(() -> new RuntimeException("User account not found for id :: " + id));
    }

    // Update a user account
    public UserAccount updateUserAccount(Long id, UserAccount userAccountDetails) {
        UserAccount userAccount = getUserAccountById(id); // Reuse getUserAccountById to ensure the user account exists

        userAccount.setUsername(userAccountDetails.getUsername());
        userAccount.setEmail(userAccountDetails.getEmail());
        userAccount.setPassword(userAccountDetails.getPassword());

        return userRepository.save(userAccount);
    }

    // Delete a user account
    public void deleteUserAccount(Long id) {
        UserAccount userAccount = getUserAccountById(id); // Reuse getUserAccountById to ensure the user account exists
        userRepository.delete(userAccount);
    }

    public UserAccount findByEmail(String email) {
        Optional<UserAccount> userAccountOptional =  userRepository.findByEmail(email);
        return userAccountOptional.orElseThrow(() -> new RuntimeException("User account not found for email: " + email));
    }
}
