package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.CardDTO;
import com.example.hbsisters.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement {
    @Autowired
    private CardRepository cardRepository;

    List<CardDTO> getCards(){
        return cardRepository
                .findAll()
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    };
    CardDTO getCardDTO(Long id){
        return cardRepository
                .findById(id)
                .map(CardDTO::new)
                .orElse(null);
    };
}
