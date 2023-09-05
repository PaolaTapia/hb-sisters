package com.example.hbsisters.services;

import com.example.hbsisters.dtos.AccountDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountDTO(Long id);

    AccountDTO findByNumber(String number);
}
