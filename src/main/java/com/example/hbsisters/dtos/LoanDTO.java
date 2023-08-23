package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Loan;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private double amount;
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    public LoanDTO(Loan loan) {
        this.id=loan.getId();
        this.name = loan.getName();
        this.amount = loan.getAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }


    public List<Integer> getPayments() {
        return payments;
    }

}
