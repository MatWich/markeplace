package com.agroniks.marketplace.item.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itemInfo")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "itemEntity")
public class ItemInfoEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    private int amount;

    @OneToOne(mappedBy = "info")
    @JsonBackReference
    private ItemEntity itemEntity;

    public ItemInfoEntity(ItemEntity itemEntity) {
        this.id = UUID.randomUUID();
        this.amount = 0;
        this.itemEntity = itemEntity;
    }
}
