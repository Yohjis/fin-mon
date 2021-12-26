package com.qpbanking.cardserice.service;


import com.qpbanking.cardserice.model.Bank;
import com.qpbanking.cardserice.model.Card;

import java.util.List;

public interface CardService {
    List<Card> getAll();
    Card getCardById(long id) throws IllegalArgumentException;
    //long createCard(Bank bank, String cardMusk);
    Card updateCard(long id, List<Bank> bank, String cardMusk) throws IllegalArgumentException;
    void deleteCard(long id);
}