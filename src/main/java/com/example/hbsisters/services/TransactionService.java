package com.example.hbsisters.services;

import com.example.hbsisters.dtos.TransactionDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions ();
    TransactionDTO getTransactionDTO(Long id);
}
