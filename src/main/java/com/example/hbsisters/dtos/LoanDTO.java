package com.example.hbsisters.dtos;

import com.example.hbsisters.models.ClientLoan;
import com.example.hbsisters.models.Loan;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private Long id;
    private String name;
    private double amount;
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    private Set<ClientLoanDTO> clients = new HashSet<>();

    public LoanDTO(Loan loan) {
        this.id=loan.getId();
        this.name = loan.getName();
        this.amount = loan.getAmount();
        this.payments = loan.getPayments();
        this.clients= loan.getClientLoans()
                .stream()
                .map(ClientLoanDTO::new)
                .collect(Collectors.toSet());
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
