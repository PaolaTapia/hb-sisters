package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.TransactionDTO;
import com.example.hbsisters.models.*;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.repositories.TransactionRepository;
import com.example.hbsisters.services.TransactionService;
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

    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransactionDTO(@PathVariable Long id){
        return transactionRepository
                .findById(id)
                .map(TransactionDTO::new)
                .orElse(null);
    }

    @Transactional
    @PostMapping(path = "/transactions")
    public ResponseEntity<Object> registerTransaction(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam  String fromAccountNumber, @RequestParam  String toAccountNumber,
            Authentication authentication)  {

        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount==0 || description.isEmpty()) {
            return new ResponseEntity<>("Missing amount or description", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing origin account number.", HttpStatus.FORBIDDEN);
        }

        if (toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing destination account number.", HttpStatus.FORBIDDEN);
        }

        Account origin_account= accountRepository.findByNumber(fromAccountNumber);

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
        Account dest_account= accountRepository.findByNumber(toAccountNumber);
        if(dest_account==null){
            return new ResponseEntity<>("Destination account number does not exist.", HttpStatus.FORBIDDEN);
        };

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("Origin account it's same that destination account.", HttpStatus.FORBIDDEN);
        }

        Transaction transaction_orig = new Transaction(TypeTransaction.DEBIT,- amount, description,LocalDate.now().atStartOfDay());
        Transaction transaction_dest = new Transaction(TypeTransaction.CREDIT,amount, description,LocalDate.now().atStartOfDay());

        origin_account.addTransaction(transaction_orig);
        origin_account.setBalance(origin_account.getBalance()-amount);
        dest_account.addTransaction(transaction_dest);
        dest_account.setBalance(dest_account.getBalance()+amount);

        transactionRepository.save(transaction_orig);
        transactionRepository.save(transaction_dest);

        accountRepository.save(origin_account);
        accountRepository.save(dest_account);

        return new ResponseEntity<>(HttpStatus.CREATED);


    }
}
