package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class LoanApplicationDTO {
    private long id;
    private String loanName, accountDestiny;
    private double amount;
    private int payments;

    public LoanApplicationDTO() {}

    public LoanApplicationDTO(int id, double amount, int payments, String accountDestinyNumber, String loanName) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestinyNumber;
        this.loanName = loanName;
    }

    public long getId() {
        return id;
    }
    public String getLoanName() {
        return loanName;
    }
    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getPayments() {
        return payments;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }
    public String getAccountDestiny() {
        return accountDestiny;
    }
    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}