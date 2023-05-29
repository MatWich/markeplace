package com.agroniks.marketplace.item.jpa;

import com.agroniks.marketplace.auction.jpa.AuctionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    private String name;

    public ItemEntity(String name, String description, double worth) {
        this.name = name;
        this.description = description;
        this.worth = worth;
    }

    private String description;
    private double worth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private AuctionEntity auction;

}
