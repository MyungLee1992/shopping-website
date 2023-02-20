package com.shoppingwebsite.shoppingwebsite.service;

import com.shoppingwebsite.shoppingwebsite.exception.CartNotFoundException;
import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.model.User;
import com.shoppingwebsite.shoppingwebsite.repository.CartRepository;
import com.shoppingwebsite.shoppingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ItemService itemService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
    }

    public Cart findCartByUser(String username) {
        User user = userRepository.findByUsername(username);
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }

        return cart;
    }

    public Cart addCartItem(String username, Long itemId) {
        Cart cart = this.findCartByUser(username);
        Item item = itemService.findItemById(itemId);
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
