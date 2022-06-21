package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number, cvv, cardHolder;
    private CardType cardType;
    private CardColor cardColor;
    private LocalDate fromDate, thruDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    private boolean active;

    public Card() {}

    public Card(String number, CardType cardType, CardColor cardColor, LocalDate fromDate, LocalDate thruDate, String cvv, Client client) {
        this.number = number;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cvv = cvv;
        this.cardHolder = client.getLastName() + " " + client.getFirstName();
        this.client = client;
        this.active = true;
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
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}

