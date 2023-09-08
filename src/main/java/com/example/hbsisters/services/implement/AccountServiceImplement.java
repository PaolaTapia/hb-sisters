package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public AccountDTO getAccountDTO(Long id) {
        return accountRepository
                .findById(id)
                .map(AccountDTO::new)
                .orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAccountsByClient(Client client) {
        return accountRepository.findByOwner(client)
                .stream()
                .map(account ->new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccountsByOwner(Client client) {
        return  accountRepository.findByOwner(client);
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }


}