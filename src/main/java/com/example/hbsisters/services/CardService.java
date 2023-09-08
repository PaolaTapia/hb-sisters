package com.example.hbsisters.services;

import com.example.hbsisters.dtos.CardDTO;
import com.example.hbsisters.models.Card;
import com.example.hbsisters.models.Client;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {
    List<CardDTO> getAllCards();
    CardDTO getCardDTO(Long id);

    List<CardDTO> getCardByHolder(Client client);

    Card getCardByNumber(String number);
    void saveCard(Card card);
}
