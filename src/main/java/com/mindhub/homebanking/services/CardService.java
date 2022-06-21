package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {
    List<CardDTO> getAll();
    CardDTO getCardById(Long id);
    Card getCard(String cardNumber);
    void saveCard(Card card);
    CardRepository getCardRepository();
}
