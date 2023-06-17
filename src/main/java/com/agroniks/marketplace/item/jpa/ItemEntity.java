package com.agroniks.marketplace.item.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemEntity", cascade = CascadeType.ALL)
    private Set<ItemInfoEntity> info = new HashSet<>();

    public ItemEntity(String name, String description, double worth) {
        this.name = name;
        this.description = description;
        this.worth = worth;
    }

    public ItemEntity(UUID id, String name, String description, double worth) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.worth = worth;
    }
}
