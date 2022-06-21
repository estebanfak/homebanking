package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransacctionService transacctionService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientLoanService clientLoanService;



    @GetMapping("/loans")
    public List<LoanDTO> getAll() {
        return loanService.getAll();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String> register(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                           Authentication authentication) {

        Account accountDest = accountService.getAccountByNumber(loanApplicationDTO.getAccountDestiny());
        Loan loan = loanService.getLoanByName(loanApplicationDTO.getLoanName());
        List<Loan> loansTaken = clientService.getClientByEmail(authentication.getName()).getLoans();

        if(loanApplicationDTO.getLoanName().isEmpty()){
            return new ResponseEntity<>("Missing loan name", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("Amount must be greater than 0", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("Payments must be greater than 0", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAccountDestiny() == null){
            return new ResponseEntity<>("Missing destination account", HttpStatus.FORBIDDEN);
        }
        if(loansTaken.contains(loan)){
            return new ResponseEntity<>("You can not have more than 1 loan of each type", HttpStatus.FORBIDDEN);
        }
        if (loan == null ) {
            return new ResponseEntity<>("This loan does not exist", HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount() ) {
            return new ResponseEntity<>("The amount requested exceeds the amount available", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments()) ) {
            return new ResponseEntity<>("Invalid number of payments", HttpStatus.FORBIDDEN);
        }
        if (accountDest == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!accountDest.getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("You are not the owner of the destination account", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.2), loanApplicationDTO.getPayments(), accountDest.getClient(), loan);
        clientLoanService.saveClientLoan(clientLoan);
        transacctionService.saveTransaction(new Transaction(CREDIT, LocalDateTime.now(), loanApplicationDTO.getAmount(), "Loan approved: " + loanApplicationDTO.getLoanName(), accountDest));
        accountDest.setBalance(accountDest.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(accountDest);
        return new ResponseEntity<>("Loan successfully created", HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------------------
    @PostMapping("/loan")
    public ResponseEntity<Object> createNewLoan(@RequestBody LoanDTO loanDTO, Authentication authentication){

        Client client = clientService.getClientByEmail(authentication.getName());


        if(client.getClientType() != ClientType.ADMIN){
            return new ResponseEntity<>("You do not have the role to do this", HttpStatus.FORBIDDEN);
        }
        if(loanService.getLoanByName(loanDTO.getName()) != null){
            return new ResponseEntity<>("The loan you want to create already exists", HttpStatus.FORBIDDEN);
        }


        Loan loan = new Loan(loanDTO.getName(), loanDTO.getMaxAmount(), loanDTO.getPayments());
        loanService.saveLoan(loan);
        return new ResponseEntity<>("Loan successfully created", HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------------------
}