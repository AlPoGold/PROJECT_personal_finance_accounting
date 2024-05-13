package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT u FROM User u WHERE u.login = :login")
//    User findByUserLogin(@Param("login") String login);


//    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}
