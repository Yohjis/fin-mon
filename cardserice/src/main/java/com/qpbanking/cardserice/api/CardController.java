package com.qpbanking.cardserice.api;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qpbanking.cardserice.model.Bank;

import com.qpbanking.cardserice.model.Card;
import com.qpbanking.cardserice.model.dto.CardDto;
import com.qpbanking.cardserice.service.impl.CardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/cards")
@RestController
public class CardController {
    private final CardServiceImpl cardService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Card>> getAll() {
        final List<Card> cards = cardService.getAll();
        return ResponseEntity.ok(cards);
    }






    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable long id) {
        try {
            Card card = cardService.getCardById(id);

            return ResponseEntity.ok(card);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

   /* @GetMapping("/dto/{id}")
    public ResponseEntity<CardDto> getDtoById(@PathVariable long id) {
        try {
            Card card = cardService.getCardById(id);
            CardDto cardDto = new CardDto(card.getBank(), card.getCardMusk());

            return ResponseEntity.ok(cardDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }*/


    /*@PostMapping("/create")
    @JsonSerialize
    public ResponseEntity<Void> create(@RequestBody Card card) {
        final Bank bank = (Bank) card.getBank();
        final String number = card.getCardMusk();

        final long cardId = cardService.createCard(bank, cardMusk);
        final String cardUri = "/card" + cardId;

        return ResponseEntity.created(URI.create(cardUri)).build();
    }*/

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody CardDto cardDto) {
        final List<Bank> bank= cardDto.getBank();
        final String cardMusk = cardDto.getCardMusk();

        try {
            cardService.updateCard(id, bank, cardMusk);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        cardService.deleteCard(id);

        return ResponseEntity.noContent().build();
    }
}