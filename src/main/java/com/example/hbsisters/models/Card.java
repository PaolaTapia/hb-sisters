package com.example.hbsisters.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private CardType type;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private ColorType color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="holder")
    private Client holder;

    public Card() {
    }

    public Card(CardType type, String number, int cvv, LocalDate fromDate, LocalDate thruDate, ColorType color, Client holder) {
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.color = color;
        this.holder = holder;
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public ColorType getColor() {
        return color;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public Client getHolder() {
        return holder;
    }

    public void setHolder(Client cardOwner) {
        this.holder = cardOwner;
    }
}
