package com.example.hbsisters.dtos;

import com.example.hbsisters.models.ClientLoan;

import javax.persistence.*;

public class ClientLoanDTO {

    private Long id;

    private double amount;
    private int payment;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loans")
    private Long loan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="clients")
    private Long client;

    public ClientLoanDTO() {
    }


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayment();
        this.loan = clientLoan.getLoan().getId();
        this.client = clientLoan.getClient().getId();
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Long getLoan() {
        return loan;
    }

    public void setLoan(Long loan) {
        this.loan = loan;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }
}