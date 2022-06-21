package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll().stream().filter(account -> account.isActive() == true).map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Set<Transaction> getTransactionsBetwen(String number, LocalDate fromDate, LocalDate toDate) {
        Set<Transaction> transactions = new HashSet<>();
        Set<Transaction> transactions2 = new HashSet<>();
        accountRepository.findByNumber(number).getTransactions().forEach(transaction -> {
            if(transaction.getTransactionDate().toLocalDate().isBefore(toDate)){
            transactions.add(transaction);
            }
        });
        transactions.forEach(transaction -> {
            if(transaction.getTransactionDate().toLocalDate().isAfter(fromDate)){
            transactions2.add(transaction);
            }
        });

        return transactions2;
    }
}