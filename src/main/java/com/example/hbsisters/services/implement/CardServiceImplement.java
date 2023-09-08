package com.example.hbsisters.services.implement;

import com.example.hbsisters.dtos.CardDTO;
import com.example.hbsisters.models.Card;
import com.example.hbsisters.models.Client;
import com.example.hbsisters.repositories.CardRepository;
import com.example.hbsisters.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<CardDTO> getAllCards(){
        return cardRepository
                .findAll()
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    };
    @Override
    public CardDTO getCardDTO(Long id){
        return cardRepository
                .findById(id)
                .map(CardDTO::new)
                .orElse(null);
    }

    @Override
    public List<CardDTO> getCardByHolder(Client client) {
        return cardRepository.findByHolder(client)
                .stream()
                .map(card ->new CardDTO(card))
                .collect(Collectors.toList());
    }

    @Override
    public Card getCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    public  void saveCard(Card card){
        cardRepository.save(card);
    };
}
