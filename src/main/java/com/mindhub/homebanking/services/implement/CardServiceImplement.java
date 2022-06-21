package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardDTO> getAll() {
        return cardRepository.findAll().stream().filter(card -> card.isActive()).map(CardDTO::new).collect(Collectors.toList());
    }
    @Override
    public CardDTO getCardById(Long id) {
        return cardRepository.findById(id).map(CardDTO::new).orElse(null);
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public Card getCard(String cardNumber) {
        return cardRepository.findByNumber(cardNumber);
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
    @Override
    public CardRepository getCardRepository() {
        return cardRepository;
    }
}
