package com.example.personal_finance_accounting.conf;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserAccountRepository repository;
    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount user = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(
                "User with email '"+username+"' not found"));
        return UserDetailsImpl.build(user);
    }
}
