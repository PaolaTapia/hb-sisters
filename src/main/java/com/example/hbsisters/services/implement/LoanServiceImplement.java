package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement {
    @Autowired
    private LoanRepository loanRepository;
    List<LoanDTO> getLoans(){     return loanRepository
            .findAll()
            .stream()
            .map(LoanDTO::new)
            .collect(Collectors.toList());};
    LoanDTO getLoanDTO( Long id){ return loanRepository
            .findById(id)
            .map(LoanDTO::new)
            .orElse(null);};
}
