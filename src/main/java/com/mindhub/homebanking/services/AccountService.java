package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AccountService {
    List<AccountDTO> getAll();
    AccountDTO getAccount(Long id);
    void saveAccount(Account account);
    AccountRepository getAccountRepository();
    Account getAccountByNumber(String number);
    Set<Transaction> getTransactionsBetwen(String number, LocalDate fromDate, LocalDate toDate);
}
