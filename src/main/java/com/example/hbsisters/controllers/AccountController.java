package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.services.AccountService;
import com.example.hbsisters.services.ClientService;
import org.jetbrains.annotations.NotNull;
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

    //@Autowiredinyeccion de dependencia
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    //Servlets
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAllAccounts();
    };

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountDTO(@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }


    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication);

        if(client.getAccounts().size()>3) {
            return new ResponseEntity<>("too many accounts", HttpStatus.FORBIDDEN);
        }

        String newNumberAccount = getNewNumberAccount();

        if(accountService.getAccountByNumber(newNumberAccount)!=null) {
            return new ResponseEntity<>("Account Number already exists", HttpStatus.FORBIDDEN);
        }

        Account account= new Account( newNumberAccount, LocalDate.now(), 0);
        client.addAccount(account);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    private static @NotNull String getNewNumberAccount() {
        String newNumberAccount="VIN-"+((int)(Math.random() * (99999999 - 10000000)) + 10000000);
        return newNumberAccount;
    }

    @GetMapping(path = "/clients/current/accounts")
    public List<AccountDTO> getAccounts (Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication) ;

        return accountService.getAccountsByClient(client);

    }


}
