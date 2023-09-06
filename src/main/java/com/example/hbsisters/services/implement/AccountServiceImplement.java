package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement {
    @Autowired
    private AccountRepository accountRepository;

    List<AccountDTO> getAllAccounts(){
        return accountRepository
                .findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    };

    AccountDTO getAccountDTO(Long id){
        return accountRepository
                .findById(id)
                .map(AccountDTO::new)
                .orElse(null);
    };

    Account findByNumber(String number){
        return accountRepository.findByNumber(number);
    };
}
