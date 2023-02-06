package com.shoppingwebsite.shoppingwebsite.service;

import com.shoppingwebsite.shoppingwebsite.exception.CartNotFoundException;
import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }

    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findCartById(Long id) {
        return cartRepository.findCartById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart by id " + id + " was not found"));
    }

    public void deleteCart(Long id) {
        cartRepository.deleteCartById(id);
    }
}
