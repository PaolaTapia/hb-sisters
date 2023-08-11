package com.example.hbsisters.models;

import com.example.hbsisters.dtos.ClientDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    //client
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner")
    private Client owner;
    public Account() {
    }

    public Account(String number, LocalDate creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    //client
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
/*
    private Map<String, Object> makePetDTO(Account account) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", account.getId());
        dto.put("name", account.getNumber();
        return dto;
    }

    private Map<String, Object> AccountDTO(Client owner) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", owner.getId());
        dto.put("firstName", owner.getFirstName());
        dto.put("lastName", owner.getLastName());
        dto.put("email", owner.getEmail());
        dto.put("client", ClientDTO(owner.getClient()));
        return dto;
    }


    public Map<String, Object> toDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", getId());
        dto.put("number", getNumber());
        return dto;
    }*/

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}
