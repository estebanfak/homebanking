package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import static com.mindhub.homebanking.utils.Utils.*;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<CardDTO> getAll() {
        return cardService.getAll();
    }
    @GetMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id) {
        return cardService.getCardById(id);
    }
//----------------------------------------------------------------------------------------------------------------------
    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> disableCard(@RequestParam String cardNumber, Authentication authentication){

        Card card = cardService.getCard(cardNumber);

        if(cardNumber.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(!clientService.getClientByEmail(authentication.getName()).getCards().contains(card)) {
            return new ResponseEntity<>("You do not own the card", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("Card disabled", HttpStatus.CREATED);
    }
//----------------------------------------------------------------------------------------------------------------------
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            @RequestParam CardType cardType,
            @RequestParam CardColor cardColor,
            Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());

        if(client.getCards().stream().filter(card -> card.isActive()).filter(card -> card.getCardType() == cardType).count() >= 3)
            return new ResponseEntity<>("Max number of cards reached", HttpStatus.FORBIDDEN);

        Card card = new Card(generateNewCardNumber(cardService.getCardRepository()), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5), getRandomCvvNumber(), client);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}