package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.LoanApplicationDTO;
import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.repositories.LoanRepository;
import com.example.hbsisters.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;

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
    @Transactional
    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO LoanApp, Authentication authentication) {
        if (clientRepository.findByEmail(authentication.getName()) != null) {
            return new ResponseEntity<>("UserName already in use", HttpStatus.FORBIDDEN);
        }

      /*  const Loan = new LoanApplicationDTO ( LoanApp.getId(),
                LoanApp.getAmount(),
                LoanApp.getPayments(),
                LoanApp.getAccountToNumber());
         loanRepository.save(Loan);*/
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
