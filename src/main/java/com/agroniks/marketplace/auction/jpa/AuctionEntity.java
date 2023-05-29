package com.agroniks.marketplace.auction.jpa;

import com.agroniks.marketplace.item.jpa.ItemEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auction")
public class AuctionEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    private String title;
    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    private ItemEntity item;

    private double currentPrice;
}
