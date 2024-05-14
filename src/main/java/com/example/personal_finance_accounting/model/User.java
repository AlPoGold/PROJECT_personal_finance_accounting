package com.example.personal_finance_accounting.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long idUser;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
}
