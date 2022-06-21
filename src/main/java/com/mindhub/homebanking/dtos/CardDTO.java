package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String number, cvv, cardHolder;
    private CardType cardType;
    private CardColor cardColor;
    private LocalDate fromDate, thruDate;
    private boolean active;


    public CardDTO() {}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cvv = card.getCvv();
        this.cardHolder = card.getCardHolder();
        this.active = card.isActive();
    }

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public CardType getCardType() {
        return cardType;
    }
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
    public CardColor getCardColor() {
        return cardColor;
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
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
    public String getCvv() {
        return cvv;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}