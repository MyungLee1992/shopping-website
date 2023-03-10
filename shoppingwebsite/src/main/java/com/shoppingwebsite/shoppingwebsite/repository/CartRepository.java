package com.shoppingwebsite.shoppingwebsite.repository;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.CartItem;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteCartById(Long id);

    Optional<Cart> findCartById(Long id);
}