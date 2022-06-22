package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.AccountType.*;
import static com.mindhub.homebanking.models.ClientType.ADMIN;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client cliente1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Client cliente2 = new Client("Esteban", "Casile", "estebanfak@gmail.com", passwordEncoder.encode("456"));
			Client cliente3 = new Client("Ricardo", "Iorio", "riorio@gmail.com", passwordEncoder.encode("789"));
			Client cliente4 = new Client("Pepe", "Lopez", "plopez@gmail.com", passwordEncoder.encode("321"));
			Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));
			admin.setClientType(ADMIN);
			clientRepository.save(cliente1);
			clientRepository.save(cliente2);
			clientRepository.save(cliente3);
			clientRepository.save(cliente4);
			clientRepository.save(admin);
			Account cuenta1 = new Account("VIN001", 5000.00, LocalDateTime.now(), SAVING ,cliente1);
			Account cuenta2 = new Account("VIN002", 7500.00, LocalDateTime.now().plusDays(1), SAVING, cliente1);
			Account cuenta3 = new Account("VIN003", 1000000.00, LocalDateTime.now(), SAVING, cliente2);
			Account cuenta4 = new Account("VIN004", 8000.00, LocalDateTime.now(), SAVING, cliente2);
			Account cuenta5 = new Account("VIN005", 300.00, LocalDateTime.now(), CHECKING, cliente2);
			accountRepository.save(cuenta1);
			accountRepository.save(cuenta2);
			accountRepository.save(cuenta3);
			accountRepository.save(cuenta4);
			accountRepository.save(cuenta5);
			Transaction transaccion1 = new Transaction(DEBIT, LocalDateTime.now(), -600.00, "alquiler", cuenta1);
			Transaction transaccion2 = new Transaction(DEBIT, LocalDateTime.now(), -350.00, "pc", cuenta1);
			Transaction transaccion3 = new Transaction(CREDIT, LocalDateTime.now(), 2000.00, "sueldo", cuenta1);
			Transaction transaccion4 = new Transaction(DEBIT, LocalDateTime.now(), -50.00, "comida", cuenta2);
			Transaction transaccion5 = new Transaction(DEBIT, LocalDateTime.now(), -750.00, "supermercado", cuenta2);
			Transaction transaccion6 = new Transaction(DEBIT, LocalDateTime.now(), -120.23, "ropa", cuenta2);
			transactionRepository.save(transaccion1);
			transactionRepository.save(transaccion2);
			transactionRepository.save(transaccion3);
			transactionRepository.save(transaccion4);
			transactionRepository.save(transaccion5);
			transactionRepository.save(transaccion6);
			//-------------------------------------------------------------------------------------------------------------------------------------
			Transaction transaccion7 = new Transaction(DEBIT, LocalDateTime.now().minusDays(10), -600.00, "alquiler", cuenta1);
			Transaction transaccion8 = new Transaction(DEBIT, LocalDateTime.now().minusDays(9), -600.00, "alquiler", cuenta1);
			Transaction transaccion9 = new Transaction(CREDIT, LocalDateTime.now().minusDays(8), 600.00, "alquiler", cuenta1);
			Transaction transaccion10 = new Transaction(DEBIT, LocalDateTime.now().minusDays(7), -600.00, "alquiler", cuenta1);
			Transaction transaccion11= new Transaction(DEBIT, LocalDateTime.now().minusDays(6), -600.00, "alquiler", cuenta1);
			Transaction transaccion12 = new Transaction(CREDIT, LocalDateTime.now().minusDays(5), 600.00, "alquiler", cuenta1);
			Transaction transaccion13 = new Transaction(DEBIT, LocalDateTime.now().minusDays(4), -600.00, "alquiler", cuenta1);
			Transaction transaccion14 = new Transaction(DEBIT, LocalDateTime.now().minusDays(3), -600.00, "alquiler", cuenta1);
			Transaction transaccion15 = new Transaction(CREDIT, LocalDateTime.now().minusDays(2), 600.00, "alquiler", cuenta1);
			Transaction transaccion16 = new Transaction(DEBIT, LocalDateTime.now().minusDays(1), -600.00, "alquiler", cuenta1);
			Transaction transaccion17 = new Transaction(DEBIT, LocalDateTime.now().plusDays(2), -600.00, "alquiler", cuenta1);
			Transaction transaccion18 = new Transaction(CREDIT, LocalDateTime.now().plusDays(3), 600.00, "alquiler", cuenta1);
			Transaction transaccion19 = new Transaction(DEBIT, LocalDateTime.now().plusDays(4), -600.00, "alquiler", cuenta1);
			Transaction transaccion20 = new Transaction(DEBIT, LocalDateTime.now().plusDays(5), -600.00, "alquiler", cuenta1);
			Transaction transaccion21= new Transaction(CREDIT, LocalDateTime.now().plusDays(6), 600.00, "alquiler", cuenta1);
			Transaction transaccion22= new Transaction(DEBIT, LocalDateTime.now().plusDays(7), -600.00, "alquiler", cuenta1);
			Transaction transaccion23= new Transaction(DEBIT, LocalDateTime.now().plusDays(8), -600.00, "alquiler", cuenta1);
			Transaction transaccion24= new Transaction(CREDIT, LocalDateTime.now().plusDays(9), 600.00, "alquiler", cuenta1);
			Transaction transaccion25= new Transaction(DEBIT, LocalDateTime.now().plusDays(10), -600.00, "alquiler", cuenta1);
			Transaction transaccion26= new Transaction(DEBIT, LocalDateTime.now().plusDays(11), -600.00, "alquiler", cuenta1);
			Transaction transaccion27= new Transaction(CREDIT, LocalDateTime.now().plusDays(12), 600.00, "alquiler", cuenta1);

			transactionRepository.save(transaccion7);
			transactionRepository.save(transaccion8);
			transactionRepository.save(transaccion9);
			transactionRepository.save(transaccion10);
			transactionRepository.save(transaccion11);
			transactionRepository.save(transaccion12);
			transactionRepository.save(transaccion13);
			transactionRepository.save(transaccion14);
			transactionRepository.save(transaccion15);
			transactionRepository.save(transaccion16);
			transactionRepository.save(transaccion17);
			transactionRepository.save(transaccion18);
			transactionRepository.save(transaccion19);
			transactionRepository.save(transaccion20);
			transactionRepository.save(transaccion21);
			transactionRepository.save(transaccion22);
			transactionRepository.save(transaccion23);
			transactionRepository.save(transaccion24);
			transactionRepository.save(transaccion25);
			transactionRepository.save(transaccion26);
			transactionRepository.save(transaccion27);
			//-------------------------------------------------------------------------------------------------------------------------------------
			Loan loan1 = new Loan("Mortgage", 500000.00, List.of(12, 24, 48, 60));
			Loan loan2 = new Loan("Personal", 100000.00, List.of(6, 12, 24));
			Loan loan3 = new Loan("Car", 300000.00, List.of(6, 12, 24, 36));
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			ClientLoan clientLoan1 = new ClientLoan(300000, 60, cliente1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, cliente1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000, 24, cliente2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36, cliente2, loan3);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			Card card1 = new Card("1234123412341234", CardType.DEBITO,CardColor.GOLD, LocalDate.now(),LocalDate.now().plusYears(5),"856", cliente1);
			Card card2 = new Card("4321432143214321", CardType.CREDITO,CardColor.TITANIUM, LocalDate.now(),LocalDate.now().plusYears(5),"642", cliente1);
			Card card3 = new Card("1111111111111111", CardType.CREDITO,CardColor.SILVER, LocalDate.now(),LocalDate.now().plusYears(5),"359", cliente2);
			Card card4 = new Card("2111111111111111", CardType.CREDITO,CardColor.GOLD, LocalDate.now(),LocalDate.now().plusDays(30),"321", cliente2);
			Card card5 = new Card("3111111111111111", CardType.CREDITO,CardColor.GOLD, LocalDate.now(),LocalDate.now().plusDays(-45),"321", cliente2);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);
			cardRepository.save(card5);




		};
	}
}