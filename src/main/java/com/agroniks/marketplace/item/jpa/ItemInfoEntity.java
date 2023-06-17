package com.agroniks.marketplace.item.jpa;

import com.agroniks.marketplace.user.jpa.UserEntity;
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
@ToString(exclude = {"itemEntity", "user"})
public class ItemInfoEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "items_id")
    @JsonBackReference
    private ItemEntity itemEntity;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private UserEntity user;

    public ItemInfoEntity(ItemEntity itemEntity) {
        this.id = UUID.randomUUID();
        this.amount = 0;
        this.itemEntity = itemEntity;
    }

    public ItemInfoEntity(ItemEntity itemEntity, int amount) {
        this.amount = amount;
        this.itemEntity = itemEntity;
    }
}
