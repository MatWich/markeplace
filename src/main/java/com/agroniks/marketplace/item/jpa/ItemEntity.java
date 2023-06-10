package com.agroniks.marketplace.item.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
@EqualsAndHashCode(of = "id")
public class ItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    private String name;
    private String description;
    private double worth;
    public ItemEntity(String name, String description, double worth) {
        this.name = name;
        this.description = description;
        this.worth = worth;
//        this.info = new ItemInfoEntity(UUID.randomUUID(), 0, this);
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemInfo_id", referencedColumnName = "id")
    private ItemInfoEntity info;

}
