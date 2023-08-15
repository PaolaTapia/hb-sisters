package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {

        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    };

    @RequestMapping("accounts/{id}")
    public AccountDTO getAccountDTO(@PathVariable Long id){

        return accountRepository
                .findById(id)
                .map(AccountDTO::new)
                .orElse(null);
    }
}
