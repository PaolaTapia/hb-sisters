package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.LoanApplicationDTO;
import com.example.hbsisters.dtos.LoanDTO;
import com.example.hbsisters.models.Account;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.models.Transaction;
import com.example.hbsisters.models.TypeTransaction;
import com.example.hbsisters.services.AccountService;
import com.example.hbsisters.services.ClientService;
import com.example.hbsisters.services.LoanService;
import com.example.hbsisters.services.TransactionService;
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
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    };

    @RequestMapping("loans/{id}")
    public LoanDTO getLoanDTO(@PathVariable Long id){
        return loanService.getLoanDTO(id);
    }
    @Transactional
    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    public ResponseEntity<String> addLoan(@RequestBody LoanApplicationDTO LoanApp, Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication);

        double amount = LoanApp.getAmount();
        String toAccountNumber = LoanApp.getToAccountNumber();
        long loanId= LoanApp.getLoanId();

        LoanDTO loan  = getLoanDTO(loanId);

        if(loan==null)  return new ResponseEntity<>("loan null", HttpStatus.FORBIDDEN);

        if(amount==0)  return new ResponseEntity<>("amount 0", HttpStatus.FORBIDDEN);
        if(amount > loan.getMaxAmount()) return new ResponseEntity<>("amount exceeds the maximum", HttpStatus.FORBIDDEN);


        if(toAccountNumber.isEmpty())  return new ResponseEntity<>("account empty", HttpStatus.FORBIDDEN);
        Account account= accountService.getAccountByNumber(toAccountNumber);
        if(account==null)  return new ResponseEntity<>("the account does not exist", HttpStatus.FORBIDDEN);
        List<Account> accounts  = accountService.getAccountsByOwner(client);
        if(!accounts.contains(account)) return new ResponseEntity<>("the account is not correct", HttpStatus.FORBIDDEN);

        Integer payments=  LoanApp.getPayments();
        if(payments==0)  return new ResponseEntity<>("payment 0", HttpStatus.FORBIDDEN);
        if(!loan.getPayments().contains(payments))  return new ResponseEntity<>("payment not included", HttpStatus.FORBIDDEN);


        Account dest_account= accountService.getAccountByNumber(toAccountNumber);

        Transaction transaction_dest = new Transaction(TypeTransaction.CREDIT,amount, loan.getName()+" loan approved",LocalDate.now().atStartOfDay());


        dest_account.addTransaction(transaction_dest);
        dest_account.setBalance(dest_account.getBalance()+amount);

        transactionService.saveTransaction(transaction_dest);
        accountService.saveAccount(dest_account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
