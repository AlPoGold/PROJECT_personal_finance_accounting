package com.example.personal_finance_accounting.repository;

import com.example.personal_finance_accounting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByUserLogin(@Param("login") String login);


    public Optional<User> findByLogin(String login);
}
