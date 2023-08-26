package com.example.hbsisters.dtos;

import com.example.hbsisters.models.Card;
import com.example.hbsisters.models.CardType;
import com.example.hbsisters.models.ColorType;

import java.time.LocalDate;

public class CardDTO {
    private Long id;

    private CardType type;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private ColorType color;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.type = card.getType();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.color = card.getColor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
