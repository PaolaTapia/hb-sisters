package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.ClientDTO;
import com.example.hbsisters.dtos.ClientLoanDTO;
import com.example.hbsisters.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientLoanController {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @RequestMapping("/client-loans")
    public List<ClientLoanDTO> getClientLoans() {

        return clientLoanRepository
                .findAll()
                .stream()
                //le paso el metodo tostream para poder usar map, un metodo de orden superior
                .map(ClientLoanDTO::new)
                .collect(Collectors.toList());
    };


    @RequestMapping("client-loans/{id}")
    public ClientLoanDTO getClientLoanDTO(@PathVariable Long id){

        return clientLoanRepository
                .findById(id)
                .map(ClientLoanDTO::new)
                .orElse(null);
    }

}
