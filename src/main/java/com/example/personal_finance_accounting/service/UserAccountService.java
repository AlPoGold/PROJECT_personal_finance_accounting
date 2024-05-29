package com.example.personal_finance_accounting.service;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {

    UserAccountRepository userRepository;
//    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}


    // Create a new user account
    public UserAccount createUserAccount(UserAccount userAccount) {
        UserAccount user =  userRepository.save(userAccount);
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
}
