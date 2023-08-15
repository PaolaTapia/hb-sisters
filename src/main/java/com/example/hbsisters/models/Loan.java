package com.example.hbsisters.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;
    private double amount;
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clients = new HashSet<>();

//private Set<ClientLoan> subscriptions = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double amount, List<Integer> payments) {
        this.name = name;
        this.amount = amount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double maxAmount) {
        this.amount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public void setClients(Set<ClientLoan> client) {
        this.clients = client;
    }

    @JsonIgnore
    public List<Client> getClients() {
        return clients.stream()
                .map(sub -> sub.getClient()).collect(toList());
    }
    public void addSubscription(ClientLoan subscription) {
        subscription.setLoan(this);
        clients.add(subscription);
    }

}
