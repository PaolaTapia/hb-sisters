package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.repositories.LoanRepository;
import com.example.hbsisters.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans(){     return loanRepository
            .findAll()
            .stream()
            .map(LoanDTO::new)
            .collect(Collectors.toList());};
    @Override
    public LoanDTO getLoanDTO( Long id){ return loanRepository
            .findById(id)
            .map(LoanDTO::new)
            .orElse(null);};
}
