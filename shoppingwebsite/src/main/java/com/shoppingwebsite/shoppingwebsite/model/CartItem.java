package com.shoppingwebsite.shoppingwebsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity = 1;
    private double price;

    public CartItem(Cart cart, Item item) {
        this.cart = cart;
        this.item = item;
        this.price = item.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getCart().getUser().getUsername(), this.getCart().getId(), this.getItem().getId());
    }
}
