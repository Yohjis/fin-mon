package com.qpbanking.cardserice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "banks")
@Data
public class Bank extends BaseEntityCard {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "banks", fetch = FetchType.LAZY)
    private List<Card> cards;

    @Override
    public String toString() {
        return "Bank{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }
}