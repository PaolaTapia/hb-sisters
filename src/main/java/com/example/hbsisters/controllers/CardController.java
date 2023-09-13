package com.example.hbsisters.controllers;

import com.example.hbsisters.dtos.CardDTO;
import com.example.hbsisters.models.*;
import com.example.hbsisters.services.CardService;
import com.example.hbsisters.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.hbsisters.utils.CardUtils.getCardNumber;
import static com.example.hbsisters.utils.CardUtils.getCvv;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
       @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getAllCards();
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCardDTO(@PathVariable Long id){
        return cardService.getCardDTO(id);
    }


    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> registerCard( @RequestParam CardType cardType, @RequestParam ColorType cardColor, Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication) ;


//también lo chequea el front
        if (cardType==null ) {
             return new ResponseEntity<>("You must select a card type", HttpStatus.FORBIDDEN);

        } else if ( cardColor==null) {
            return new ResponseEntity<>("You must select a card color", HttpStatus.FORBIDDEN);
        }

        Set<Card> cards= client.getCards();
        Set<Card> cards2=cards.stream().filter(card -> (card.getType() == cardType)).collect(Collectors.toSet());
        Set<Card> cards3=cards.stream().filter(card -> (card.getType() == cardType && card.getColor() == cardColor)).collect(Collectors.toSet());

        if(cards2.size()>=3) {return new ResponseEntity<>("too many cards", HttpStatus.FORBIDDEN);}
        if(!cards3.isEmpty()) {return new ResponseEntity<>("too many cards", HttpStatus.FORBIDDEN);}

        String newNumberCard = getCardNumber();
        if(cardService.getCardByNumber(newNumberCard)!=null) {
            return new ResponseEntity<>("Card Number already exists", HttpStatus.FORBIDDEN);
        }
int cvv= getCvv();
        Card card= new Card( cardType, newNumberCard, cvv, LocalDate.now(),LocalDate.now().plusYears(5), cardColor,client);


        client.addCard(card);
        cardService.saveCard(card);
        return new ResponseEntity<>("card created!",HttpStatus.CREATED);

    }

    @RequestMapping(path = "/clients/current/cards")
    public List<CardDTO> getCards (Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication) ;

        return cardService.getCardByHolder(client);

    }

}
