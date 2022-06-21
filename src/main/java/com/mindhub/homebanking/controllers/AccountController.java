package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.PaymentsDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransacctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.AccountType.*;
import static com.mindhub.homebanking.utils.Utils.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CardService cardService;
    @Autowired
    private TransacctionService transacctionService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        return accountService.getAll();
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType accountType,
            Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());

        if(client.getAccounts().stream().filter(Account::isActive).count() >= 3){
            return new ResponseEntity<>("Max account number reached", HttpStatus.FORBIDDEN);
        }
        if(accountType.toString().isEmpty()){
            return new ResponseEntity<>("Account type requiered", HttpStatus.FORBIDDEN);
        }
        if(accountType != SAVING && accountType != CHECKING){
            return new ResponseEntity<>("Account type must be SAVING or CHECKING", HttpStatus.FORBIDDEN);
        }
        Account account = new Account(generateNewAccountNumber(accountService.getAccountRepository()), 0.00, LocalDateTime.now(), accountType, client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //-------------------------------------------DISABLE ACCOUNTS-------------------------------------------------------
    @PatchMapping("/clients/current/account")
    public ResponseEntity<Object> disableAccounts(@RequestParam String accountNumber, Authentication authentication){

        Account account = accountService.getAccountByNumber(accountNumber);

        if(accountNumber.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(!clientService.getClientByEmail(authentication.getName()).getAccounts().contains(account)) {
            return new ResponseEntity<>("You do not own the account", HttpStatus.FORBIDDEN);
        }
        if(accountService.getAccountByNumber(accountNumber).getBalance() != 0){
            return new ResponseEntity<>("You can not close an account with balance different to 0", HttpStatus.FORBIDDEN);
        }
        account.setActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Account disabled", HttpStatus.CREATED);
    }
//----------------------------------------------------------------------------------------------------------------------
// -------------------------------------------PAYMENTS------------------------------------------------------------------
    @PostMapping("/clients/current/account/payments")
    public ResponseEntity<Object> payments(@RequestBody PaymentsDTO paymentsDTO, Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());

        if (paymentsDTO.getNumber().isEmpty()) {
            return new ResponseEntity<>("Missing data: Number", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getSecurityCode().isEmpty()) {
            return new ResponseEntity<>("Missing data: Security code", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getAmount() <= 0) {
            return new ResponseEntity<>("Missing data: Amount must be above 0", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getDetail().isEmpty()) {
            return new ResponseEntity<>("Missing data: Detail", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getPayingMethod().isEmpty()) {
            return new ResponseEntity<>("Missing data: Paying method", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        Card card = null;

        if (client.getAccounts().contains(accountService.getAccountByNumber(paymentsDTO.getPayingMethod()))) {
            account = accountService.getAccountByNumber(paymentsDTO.getPayingMethod());
        }
        if (client.getCards().contains(cardService.getCard(paymentsDTO.getNumber()))) {
            card = cardService.getCard(paymentsDTO.getPayingMethod());
        }
        if (card == null && account == null){
            return new ResponseEntity<>("You do not own the paying method", HttpStatus.FORBIDDEN);
        }

        assert account != null;
        if(paymentsDTO.getAmount() > account.getBalance()){
            return new ResponseEntity<>("Account balance not enough", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(TransactionType.DEBIT, LocalDateTime.now(), -paymentsDTO.getAmount(), paymentsDTO.getDetail() + " " + "Payment number: " + paymentsDTO.getNumber(), account);
        account.setBalance(account.getBalance()-paymentsDTO.getAmount());
        transacctionService.saveTransaction(transaction);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Successful payment", HttpStatus.CREATED);
    }
//----------------------------------------------------------------------------------------------------------------------
}