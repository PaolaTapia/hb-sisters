package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Transaction;
import com.example.hbsisters.models.TypeTransaction;

import java.time.LocalDate;

public class TransactionDTO {

    private Long id;
    private TypeTransaction type ;
    private double ammount ;
    private  String descrption;
    private LocalDate date;

    public TransactionDTO(Transaction transaction) {
        this.type = transaction.getType();
        this.ammount = transaction.getAmmount();
        this.descrption = transaction.getDescrption();
        this.date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }

    public TypeTransaction getType() {
        return type;
    }

    public double getAmmount() {
        return ammount;
    }

    public String getDescrption() {
        return descrption;
    }

    public LocalDate getDate() {
        return date;
    }
}
