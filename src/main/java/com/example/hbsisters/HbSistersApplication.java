package com.example.hbsisters;

import com.example.hbsisters.models.*;
import com.example.hbsisters.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HbSistersApplication {
@Autowired
private PasswordEncoder passwordEncoder;
 	public static void main(String[] args) {
		SpringApplication.run(HbSistersApplication.class, args);
	}

	LocalDate myObj = LocalDate.now(); // Create a date object Display the current date
	LocalDate date2 = LocalDate.now().plusYears(4); // Create a date object Display the current date
	List<Integer> payments1 =List.of(12,24,36,48,60);
	List<Integer> payments2 = List.of(6, 12,24);

	List<Integer> payments3= List.of(6, 12,24,36);
     //
	@Bean
	CommandLineRunner initData(
			ClientRepository clientRepository,
			AccountRepository accountRepository,
			TransactionRepository transactionRepository,
			LoanRepository loanRepository,
			ClientLoanRepository clientLoanRepository,
			CardRepository cardRepository
			){
		return args -> {
//la clave del uno va a la del mucho

			Transaction transaction1= new Transaction(TypeTransaction.CREDIT,1000,"retiro por cajero", myObj.atStartOfDay());
			Transaction transaction2= new Transaction(TypeTransaction.DEBIT,1000,"transferencia", myObj.atStartOfDay());
			Transaction transaction3= new Transaction(TypeTransaction.DEBIT,1000,"retiro por cajero", myObj.atStartOfDay());
			Transaction transaction4= new Transaction(TypeTransaction.DEBIT,1000,"transferencia", myObj.atStartOfDay());

			Account account1=new Account("VIN001", myObj, 10.0);
			Account account2=new Account("VIN002", myObj, 20.0);
			Account account3=new Account("VIN003", myObj, 30.0);

			Client cliente1=new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			Client cliente2=new Client("Rodrigo", "Ribeiro", "rodrigo@mindhub.com",passwordEncoder.encode( "rodri"));
			Client cliente3=new Client("Rodrigo", "Ribeiro", "admin@mindhub.com",passwordEncoder.encode( "rodri"));

			Loan loan1= new Loan("Hipotecario", 500.000, payments1);
			Loan loan2= new Loan("Personal", 100.000, payments2);
			Loan loan3= new Loan("Automotriz", 300.000, payments3);

			Card card1= new Card(CardType.DEBIT, "1234-5678-9012-3456", 123, myObj,date2, ColorType.GOLD,cliente1);
			Card card2= new Card(CardType.CREDIT, "1234-5678-9012-3452", 125, myObj,date2, ColorType.TITANIUM,cliente1);


				cliente1.addAccount(account2);
				cliente1.addAccount(account1);
				cliente2.addAccount(account3);

			ClientLoan clientLoan1= new ClientLoan( 400.000, 60);
			ClientLoan clientLoan2= new ClientLoan( 50.000, 60);
			ClientLoan clientLoan3= new ClientLoan( 150.000, 60);

				loan1.addClientLoan(clientLoan1);
				loan2.addClientLoan(clientLoan2);
				loan3.addClientLoan(clientLoan3);

				cliente1.addClientLoan(clientLoan1);
				cliente2.addClientLoan(clientLoan3);


			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);


			clientRepository.save(cliente1);
			clientRepository.save(cliente2);
			clientRepository.save(cliente3);


			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			cardRepository.save(card1);
			cardRepository.save(card2);
		};
	}
}
