package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.LoanApplicationDTO;
import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.models.Transaction;
import com.example.hbsisters.models.TypeTransaction;
import com.example.hbsisters.repositories.AccountRepository;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.repositories.LoanRepository;
import com.example.hbsisters.repositories.TransactionRepository;
import com.example.hbsisters.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
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
        Client client = clientRepository.findByEmail(authentication.getName());

        double amount = LoanApp.getAmount();
        String toAccountNumber = LoanApp.getToAccountNumber();
        long loanId= LoanApp.getLoanId();

        LoanDTO loan  = getLoanDTO(loanId);

        if(loan==null)  return new ResponseEntity<>("loan null", HttpStatus.FORBIDDEN);

        if(amount==0)  return new ResponseEntity<>("amount 0", HttpStatus.FORBIDDEN);
        if(amount > loan.getMaxAmount()) return new ResponseEntity<>("amount exceeds the maximum", HttpStatus.FORBIDDEN);


        if(toAccountNumber.isEmpty())  return new ResponseEntity<>("account empty", HttpStatus.FORBIDDEN);
        Account account= accountRepository.findByNumber(toAccountNumber);
        if(account==null)  return new ResponseEntity<>("the account does not exist", HttpStatus.FORBIDDEN);
        List<Account> accounts  = accountRepository.findByOwner(client);
        if(!accounts.contains(account)) return new ResponseEntity<>("the account is not correct", HttpStatus.FORBIDDEN);

        Integer payments=  LoanApp.getPayments();
        if(payments==0)  return new ResponseEntity<>("payment 0", HttpStatus.FORBIDDEN);
        if(!loan.getPayments().contains(payments))  return new ResponseEntity<>("payment not included", HttpStatus.FORBIDDEN);


        Account dest_account= accountRepository.findByNumber(toAccountNumber);

        Transaction transaction_dest = new Transaction(TypeTransaction.CREDIT,amount, loan.getName()+" loan approved",LocalDate.now().atStartOfDay());


        dest_account.addTransaction(transaction_dest);
        dest_account.setBalance(dest_account.getBalance()+amount);

        transactionRepository.save(transaction_dest);
        accountRepository.save(dest_account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
