package com.mindhub.homebanking.controllers;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransacctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import static com.mindhub.homebanking.models.TransactionType.*;


@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransacctionService transacctionService;

//---------------------------------------- PRUEBAS ---------------------------------------------------------------------
    @GetMapping("/transactions/destination")
    public String getName(@RequestParam String email) {

        String clientName = accountService.getAccountByNumber(email).getClient().getFirstName();
        String clientLastName = accountService.getAccountByNumber(email).getClient().getLastName();
        return clientLastName + ", " + clientName;
    }
//----------------------------------------------------------------------------------------------------------------------
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            @RequestParam double amount,
            @RequestParam String detail,
            @RequestParam String accountOriginNumber,
            @RequestParam String accountDestinationNumber,
            Authentication authentication) {

        Client clientAuthenticated = clientService.getClientByEmail(authentication.getName());
        Account accountOrigin = accountService.getAccountByNumber(accountOriginNumber);
        Account accountDestination = accountService.getAccountByNumber(accountDestinationNumber);


        if(amount <= 0) {
            return new ResponseEntity<>("Amount can not be negative or 0", HttpStatus.FORBIDDEN);
        }
        if(detail.isEmpty()) {
            return new ResponseEntity<>("Missing data: detail", HttpStatus.FORBIDDEN);
        }
        if(accountOriginNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data: Account origin number", HttpStatus.FORBIDDEN);
        }
        if(accountDestinationNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data: Account destination number", HttpStatus.FORBIDDEN);
        }
        if(accountOriginNumber == accountDestinationNumber) {
            return new ResponseEntity<>("The destination account must be different of the origin account", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin == null) {
            return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
        }
        if(!clientAuthenticated.getAccounts().contains(accountOrigin)) {
            return new ResponseEntity<>("You do not own the origin account", HttpStatus.FORBIDDEN);
        }
        if(accountDestination == null) {
            return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient balance to make the transaction", HttpStatus.FORBIDDEN);
        }
        Transaction transactionDebit = new Transaction(DEBIT, LocalDateTime.now(), -amount, "Transfered to " + accountDestinationNumber + " - " + detail, accountOrigin);
        Transaction transactionCredit = new Transaction(CREDIT, LocalDateTime.now(), amount, "Transfered from " + accountOriginNumber + " - " + detail, accountDestination);
        transacctionService.saveTransaction(transactionDebit);
        transacctionService.saveTransaction(transactionCredit);
        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestination);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/pdf/transactions")
    public ResponseEntity<Object> getTransactionsPDF(Authentication authentication,
                                                     @RequestParam String accountNumber,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) throws FileNotFoundException {

        Client client = clientService.getClientByEmail(authentication.getName());
        Account account = accountService.getAccountByNumber(accountNumber);
        Set<Transaction> transactions = account.getTransactions();
        Set<Transaction> transactionSet = null;

        if(fromDate != null && toDate != null) {
            transactionSet = accountService.getTransactionsBetwen(accountNumber, fromDate.minusDays(1), toDate.plusDays(1));
        }else{
            transactionSet = transactions;
        }
        System.out.println(transactions);
        System.out.println(transactionSet);

        if(accountNumber.isEmpty()){
            return new ResponseEntity<>("Missing data: Account number", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("You are not the owner of the account", HttpStatus.FORBIDDEN);
        }
        LocalDateTime localDateTime = LocalDateTime.now();

        String file_name="D:\\pdf\\prueba" + localDateTime + ".pdf";
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(file_name));

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Transactions", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph1 = new Paragraph("Account: " + account.getNumber(), fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_LEFT);

        PdfPTable table = new PdfPTable(4);
        PdfPCell c1 = new PdfPCell(new Phrase("Date"));
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Detail"));
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Amount"));
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Balance"));
        table.addCell(c1);

        table.setHeaderRows(1);

        transactionSet.forEach(transaction -> {
            String year = String.valueOf(transaction.getTransactionDate().getYear());
            String month = String.valueOf(transaction.getTransactionDate().getMonthValue());
            String day = String.valueOf(transaction.getTransactionDate().getDayOfMonth());
            String balance = String.valueOf(transaction.getBalanceAccount());
            table.addCell(year + "-" + month + "-" + day);
            table.addCell(transaction.getDetail());
            table.addCell(String.valueOf(transaction.getAmount()));
            table.addCell(balance);

        });



        document.add(paragraph);
        document.add(new Paragraph(" "));
        document.add(paragraph1);
        document.add(new Paragraph(" "));
        document.add(table);

        document.close();

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //------------------------------------------------------------------------------------------------------------------
}