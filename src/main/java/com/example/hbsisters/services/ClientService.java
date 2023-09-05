package com.example.hbsisters.services;

import com.example.hbsisters.dtos.ClientDTO;
import com.example.hbsisters.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAllClients();
    ClientDTO getClient(Long id);
    ClientDTO getCurrentClient(Authentication authentication);

    void saveClient(Client client);

}
