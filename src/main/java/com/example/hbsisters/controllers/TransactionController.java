package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.AccountDTO;
import com.example.hbsisters.dtos.TransactionDTO;
import com.example.hbsisters.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions (){

        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }
}
