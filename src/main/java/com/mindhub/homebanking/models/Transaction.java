package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    //propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime transactionDate;
    private TransactionType type;
    private double amount;
    private String detail;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    private double balanceAccount;

    //Constructor vacío
    public Transaction() {}

    //Constructor con parámetros
    public Transaction(TransactionType type,LocalDateTime transactionDate, double amount, String detail, Account account) {
        this.type = type;
        this.account = account;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.detail = detail;
        this.balanceAccount = account.getBalance() + amount;
    }

    //Getter y Setters
    public long getId() {return id;}
    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}
    public Account getAccount() {
        return account;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {this.amount = amount;}
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public double getBalanceAccount() {
        return balanceAccount;
    }
    public void setBalanceAccount(double balanceAccount) {
        this.balanceAccount = balanceAccount;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
