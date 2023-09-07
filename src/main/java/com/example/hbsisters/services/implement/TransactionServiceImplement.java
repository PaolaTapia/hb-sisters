package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.TransactionDTO;
import com.example.hbsisters.repositories.TransactionRepository;
import com.example.hbsisters.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
  public   List<TransactionDTO> getTransactions (){
        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    };
    @Override
   public TransactionDTO getTransactionDTO(Long id){
        return transactionRepository
                .findById(id)
                .map(TransactionDTO::new)
                .orElse(null);
    };
}
