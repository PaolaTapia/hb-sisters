package com.example.hbsisters;

import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HbSistersApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbSistersApplication.class, args);
	}

	LocalDate myObj = LocalDate.now(); // Create a date object
     // Display the current date
	@Bean
	CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
//la clave del uno va a la del mucho
			Account account1=new Account("VIN001", myObj, 10.0);
			Account account2=new Account("VIN002", myObj, 20.0);

			Client cliente1=new Client("Melba", "Morel", "melba@mindhub.com");

			clientRepository.save(cliente1);//	genero el ID

				cliente1.addAccount(account2);

				cliente1.addAccount(account1);
			accountRepository.save(account1);
			accountRepository.save(account2);
		//	System.out.println(account1);


		};
	}
}
