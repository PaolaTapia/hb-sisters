package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.ClientDTO;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.ClientRepository;
import com.example.hbsisters.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
@Autowired
private ClientRepository clientRepository;
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository
                .findById(id)
                .map(ClientDTO::new)
                .orElse(null);
    }

    @Override
    public Client getCurrentClient(Authentication authentication) {
        return  clientRepository
                .findByEmail(authentication.getName());
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }


}
