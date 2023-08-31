package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.ClientDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    //    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    @RequestMapping("/clients")
    public List<ClientDTO> getClients (){

        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    };


    @RequestMapping("/clients/{id}")
    public ClientDTO getClientDTO(@PathVariable Long id){

        return clientRepository
                .findById(id)
                .map(ClientDTO::new)
                .orElse(null);
    }
    @RequestMapping("/clients/current")
    public ClientDTO getClientDTO(Authentication authentication){

        return  new ClientDTO( clientRepository
                .findByEmail(authentication.getName()));
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
           if(firstName.isEmpty() ) return new ResponseEntity<>("Missing firstName", HttpStatus.FORBIDDEN);
           if(lastName.isEmpty() ) return new ResponseEntity<>("Missing lastName", HttpStatus.FORBIDDEN);
           if(email.isEmpty() ) return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
           return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }


        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("UserName already in use", HttpStatus.FORBIDDEN);
        }

        Client client= new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        Account account= new Account( "VIN-"+((int)(Math.random() * (99999999 - 10000000)) + 10000000), LocalDate.now(), 0);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
