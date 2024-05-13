package com.example.personal_finance_accounting.service;


import com.example.personal_finance_accounting.model.User;
import com.example.personal_finance_accounting.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@AllArgsConstructor
@Service
@Log
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    public void saveUser(User user){
        user.setPassword(encoder().encode(user.getPassword()));
        log.log(Level.INFO, "user was added in user service" + user.getEmail() +  user.getPassword());
        userRepository.save(user);
    }
}
