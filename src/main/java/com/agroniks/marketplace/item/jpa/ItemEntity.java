package com.agroniks.marketplace.item.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity(name = "ITEM_ENTITY")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
@EqualsAndHashCode(of = "id")
public class ItemEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @ColumnDefault("random_uuid()")
    @Column(name = "id", updatable = false, nullable = false)
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
