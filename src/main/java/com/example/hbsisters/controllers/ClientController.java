package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.ClientDTO;
import com.example.hbsisters.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients (){

        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    };


    @RequestMapping("clients/{id}")
    public ClientDTO getClientDTO(@PathVariable Long id){

        return clientRepository
                .findById(id)
                .map(ClientDTO::new)
                .orElse(null);
    }
}