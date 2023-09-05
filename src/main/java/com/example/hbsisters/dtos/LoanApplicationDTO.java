package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Loan;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanApplicationDTO {

        private Long loanId;
        private double amount;
        private Integer payments;
        private String accountToNumber;

    public Long getId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getAccountToNumber() {
        return accountToNumber;
    }

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(double amount, Integer payments, String accountToNumber) {
        this.amount = amount;
        this.payments = payments;
        this.accountToNumber = accountToNumber;
    }
}

