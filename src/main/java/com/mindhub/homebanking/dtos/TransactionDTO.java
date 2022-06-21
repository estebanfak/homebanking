package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private LocalDateTime transactionDate;
    private TransactionType type;
    private double amount;
    private String detail;
    private double balanceAccount;

    public TransactionDTO() {}
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionDate = transaction.getTransactionDate();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.detail = transaction.getDetail();
        this.balanceAccount = transaction.getBalanceAccount();
    }

    public long getId() {
        return id;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
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
}