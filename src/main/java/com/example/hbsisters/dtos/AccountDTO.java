package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;

import java.time.LocalDate;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;

    public AccountDTO(Account acount) {
        this.id = acount.getId();
        this.number = acount.getNumber();
        this.creationDate = acount.getCreationDate();
        this.balance = acount.getBalance();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }
}
