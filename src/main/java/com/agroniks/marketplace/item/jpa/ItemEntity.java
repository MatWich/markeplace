package com.agroniks.marketplace.item.jpa;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    private String name;
    private String description;
    private double value;
    public ItemEntity(String name, String description, double value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

}
