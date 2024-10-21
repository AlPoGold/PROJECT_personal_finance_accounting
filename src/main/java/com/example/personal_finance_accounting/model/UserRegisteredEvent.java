package com.example.personal_finance_accounting.model;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {
    private final UserAccount userAccount;

    public UserRegisteredEvent(Object source, UserAccount userAccount) {
        super(source);
        this.userAccount = userAccount;
    }

}
