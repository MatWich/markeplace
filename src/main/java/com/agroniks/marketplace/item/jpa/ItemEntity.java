package com.agroniks.marketplace.item.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;

    private String name;

    public ItemEntity(String name, String description, double value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    private String description;
    private double value;

}
