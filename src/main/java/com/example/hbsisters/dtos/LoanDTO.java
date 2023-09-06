package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Loan;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private final Long id;
    private final String name;
    private final double maxAmount;
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    private Set<ClientLoanDTO> clients = new HashSet<>();

    public LoanDTO(Loan loan) {
        this.id=loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
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

    public double getMaxAmount() {
        return maxAmount;
    }


    public List<Integer> getPayments() {
        return payments;
    }

}
