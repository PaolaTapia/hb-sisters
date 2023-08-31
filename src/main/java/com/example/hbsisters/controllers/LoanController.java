package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {

        return loanRepository
                .findAll()
                .stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
    };

    @RequestMapping("loans/{id}")
    public LoanDTO getLoanDTO(@PathVariable Long id){

        return loanRepository
                .findById(id)
                .map(LoanDTO::new)
                .orElse(null);
    }
}
