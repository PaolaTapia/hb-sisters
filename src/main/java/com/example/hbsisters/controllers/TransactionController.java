package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.TransactionDTO;
import com.example.hbsisters.models.*;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    //servlets
    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions (){

        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping("transactions/{id}")
    public TransactionDTO getTransactionDTO(@PathVariable Long id){

        return transactionRepository
                .findById(id)
                .map(TransactionDTO::new)
                .orElse(null);
    }

    @Transactional
    @PostMapping(path = "/clients/current/transactions")
    public ResponseEntity<Object> register(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam  String number_origin, @RequestParam  String number_dest,
            Authentication authentication)  {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount==0 || description.isEmpty()) {
            return new ResponseEntity<>("Missing amount or description", HttpStatus.FORBIDDEN);
        }

        if (number_origin.isEmpty()) {
            return new ResponseEntity<>("Missing origin account number.", HttpStatus.FORBIDDEN);
        }

        if (number_dest.isEmpty()) {
            return new ResponseEntity<>("Missing destination account number.", HttpStatus.FORBIDDEN);
        }

        Account origin_account= accountRepository.findByNumber(number_origin);

        if(origin_account==null){
            return new ResponseEntity<>("Origin account number does not exist.", HttpStatus.FORBIDDEN);
        };

        if(origin_account.getBalance()<amount){
            return new ResponseEntity<>("You have no available balance.", HttpStatus.FORBIDDEN);
        };

        List<Account> accounts= accountRepository.findByOwner(client);

        //if(origin_account.getOwner() == client){
        if(!accounts.contains(origin_account)){
            return new ResponseEntity<>("Origin account number does not belong to you.", HttpStatus.FORBIDDEN);
        };

        if(accountRepository.findByNumber(number_dest)==null){
            return new ResponseEntity<>("Destination account number does not exist.", HttpStatus.FORBIDDEN);
        };

        if (number_origin.equals(number_dest)) {
            return new ResponseEntity<>("Origin account it's same that destination account.", HttpStatus.FORBIDDEN);
        }

        Transaction transaction_orig = new Transaction(TypeTransaction.DEBIT,amount, "transferencia enviada",LocalDate.now().atStartOfDay());
        origin_account.addTransaction(transaction_orig);
        Transaction transaction_dest = new Transaction(TypeTransaction.CREDIT,amount, "transferencia recibida",LocalDate.now().atStartOfDay());
        origin_account.addTransaction(transaction_dest);
        transactionRepository.save(transaction_orig);
        transactionRepository.save(transaction_dest);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }
}
