package com.qpbanking.cardserice.model.dto;

import com.qpbanking.cardserice.model.Bank;
import com.qpbanking.cardserice.model.Card;
import com.qpbanking.cardserice.model.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class CardDto {

    private Long id;
    private String cardMusk;
    private String Iban;
    private List<Bank> bank;
    private String cvv;
    private String exp;
    private String password;
    private String status;

    public Card toCard() {
        Card card = new Card();
        card.setId(id);
        card.setCardMusk(cardMusk);
        card.setIBan(Iban);
        card.setExp(exp);
        card.setStatus(Status.valueOf(status));
        return card;
    }


}