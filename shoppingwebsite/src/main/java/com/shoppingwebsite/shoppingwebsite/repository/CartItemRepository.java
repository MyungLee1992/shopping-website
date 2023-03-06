package com.shoppingwebsite.shoppingwebsite.repository;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.CartItem;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndItem(Cart cart, Item item);

    List<CartItem> findByCart(Cart cart);

    void deleteCartItemById(Long id);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart = :cart")
    void deleteAllByCart(@Param("cart") Cart cart);
}
