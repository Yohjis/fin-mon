package com.qpbanking.cardserice.model;


import lombok.Data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
public class Card extends BaseEntityCard {

    @Column(name = "cardMusk")
    private String cardMusk;

    @Column(name = "IBan")
    private String IBan;

    @Column(name = "CVV")
    private String CVV;

    @Column(name = "exp")
    private String exp;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "card_banks",
            joinColumns = {@JoinColumn(name = "card_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "bank_id", referencedColumnName = "id")})
    private List<Bank> banks;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("cardMusk=").append(cardMusk);
        sb.append(", password='").append(password).append('\'');
        sb.append(", bank=").append(banks);
        sb.append('}');
        return sb.toString();
    }
}