package com.example.hbsisters.dtos;

public class LoanApplicationDTO {

        private Long loanId;
        private double amount;
        private Integer payments;
        private String toAccountNumber;

    public Long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

}

