package com.example.personal_finance_accounting.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="amount")
    private BigDecimal amount;
    @Column(name="category")
    private String category;
    @Column(name="date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "userId",  nullable = true)
    private UserAccount userAccount;
}
