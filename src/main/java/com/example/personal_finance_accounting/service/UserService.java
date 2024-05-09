package com.example.personal_finance_accounting.service;


import com.example.personal_finance_accounting.model.User;
import com.example.personal_finance_accounting.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}

    public void saveUser(User user){
        user.setPassword(encoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}
