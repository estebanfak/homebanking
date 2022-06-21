package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public Loan getLoanByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}