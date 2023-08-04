package com.example.hbsisters;

import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HbSistersApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbSistersApplication.class, args);
	}


	@Bean
	CommandLineRunner initData(ClientRepository clientRepository){
		return args -> {
			Client cliente1= new Client( "juan perez");
			clientRepository.save(cliente1);

		};
	}
}
