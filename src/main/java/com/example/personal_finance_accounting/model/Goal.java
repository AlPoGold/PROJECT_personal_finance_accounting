package com.example.personal_finance_accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "targetAmount")
    private Double targetAmount;

    @Column(name = "currentAmount")
    private Double currentAmount = 0.00;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GoalStatusEnum status = GoalStatusEnum.NOT_STARTED;

    @ManyToOne
    @JoinColumn(name = "userId",  nullable = true)
    private UserAccount userAccount;
}
