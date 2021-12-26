package com.qpbanking.cardserice.service.impl;


import com.qpbanking.cardserice.model.Bank;

import com.qpbanking.cardserice.model.Card;
import com.qpbanking.cardserice.repository.CardRepository;
import com.qpbanking.cardserice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;



    @Override
    public List<Card> getAll() {
        List<Card> result = cardRepository.findAll();
        //System.out.println("IN getAll - {} users found", result.size());
        return result;
    }
    
    @Override
    public Card getCardById(long id) throws IllegalArgumentException {
        final Optional<Card> optionalCard = cardRepository.findById(id);

        if (optionalCard.isPresent())
            return optionalCard.get();
        else
            throw new IllegalArgumentException("Invalid Card ID");
    }

    @Override
    public Card updateCard(long id, List<Bank> bank, String cardMusk) throws IllegalArgumentException {
        final Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty())
            throw new IllegalArgumentException("Invalid Card ID");

        final Card card = optionalCard.get();
        if (bank != null) card.setBanks(card.getBanks());
        if (cardMusk != null && !cardMusk.isBlank()) card.setCardMusk(cardMusk);
        cardRepository.save(card);
        return null;
    }

   /* @Override
    public long createCard(Bank bank, String cardMusk) {
        final Card card = new Card(bank, cardMusk);
        final Card savedCard = cardRepository.save(card);

        return savedCard.getId();
    }*/

   /* @Override
    public Card updateCard(long id, Bank bank, String cardMusk)
            throws IllegalArgumentException {
        final Optional<Card> optionalCard = cardRepository.findById(id);

        if (optionalCard.isEmpty())
            throw new IllegalArgumentException("Invalid Card ID");

        final Card card = optionalCard.get();
        if (bank != null) card.setBank((List<Bank>) bank);
        if (cardMusk != null && !cardMusk.isBlank()) card.setCardMusk(cardMusk);
        cardRepository.save(card);
    }*/

    @Override
    public void deleteCard(long id) {
        cardRepository.deleteById(id);
    }
}