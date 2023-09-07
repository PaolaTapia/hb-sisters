package com.example.hbsisters.services;

import com.example.hbsisters.dtos.CardDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {
    List<CardDTO> getCards();
    CardDTO getCardDTO(Long id);
}
