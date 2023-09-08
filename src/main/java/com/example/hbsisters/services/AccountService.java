package com.example.hbsisters.services;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountDTO(Long id);

    List<AccountDTO> getAccountsByClient(Client client);
    List<Account> getAccountsByOwner(Client client);
    Account getAccountByNumber(String number);

    void saveAccount(Account account);

}
