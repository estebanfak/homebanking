package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName, lastName, email;
    private Set<AccountDTO> accounts =new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();
    private ClientType clientType;

    public ClientDTO(){}
    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().filter(account -> account.isActive()).map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards = client.getCards().stream().filter(card -> card.isActive()).map(card -> new CardDTO(card)).collect(Collectors.toSet());
        this.clientType = client.getClientType();
    }

    public long getId() {return id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public Set<AccountDTO> getAccounts() {return accounts;}
    public void setAccounts(Set<AccountDTO> accounts) {this.accounts = accounts;}
    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public Set<CardDTO> getCards() {
        return cards;
    }
    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }
    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }
    public ClientType getClientType() {
        return clientType;
    }
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}