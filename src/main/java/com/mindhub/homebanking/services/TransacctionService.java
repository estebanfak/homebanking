package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TransacctionService {
    void saveTransaction(Transaction transaction);
}
