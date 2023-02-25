package com.shoppingwebsite.shoppingwebsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "item_id")
    private Item item;


    private int quantity = 1;

    public CartItem(Cart cart, Item item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCart().getUser().getUsername(), this.getCart().getId(), this.getItem().getId());
    }
}
