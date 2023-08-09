package com.example.hbsisters.controllers;

import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @JsonIgnore
    @RequestMapping("/clients")
    public List<Client> getClients (){
        return clientRepository.findAll();
    };

    @RequestMapping("/accounts")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    };
}