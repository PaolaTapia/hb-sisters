package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.services.AccountService;
import com.example.hbsisters.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    //inyeccion de dependencia
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    };

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountDTO(@PathVariable Long id){

        return accountRepository
                .findById(id)
                .map(AccountDTO::new)
                .orElse(null);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if(client.getAccounts().size()>3) {return new ResponseEntity<>("too many accounts", HttpStatus.FORBIDDEN);}

        String newNumberAccount="VIN-"+((int)(Math.random() * (99999999 - 10000000)) + 10000000);
        if(accountRepository.findByNumber(newNumberAccount)!=null) {
            return new ResponseEntity<>("Account Number already exists", HttpStatus.FORBIDDEN);
        }

        Account account= new Account( newNumberAccount, LocalDate.now(), 0);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping(path = "/clients/current/accounts")
    public List<AccountDTO> getAccounts (Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        return accountRepository.findByOwner(client)
                .stream()
                .map(account ->new AccountDTO(account))
                .collect(Collectors.toList());

    }
}
