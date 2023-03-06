package com.shoppingwebsite.shoppingwebsite.service;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.CartItem;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.model.User;
import com.shoppingwebsite.shoppingwebsite.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ItemService itemService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
    }

    public List<CartItem> findCartItemsByUser(String username) {
        User user = userRepository.findByUsername(username);
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }

        return cartItemRepository.findByCart(cart);
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

    public CartItem addCartItem(String username, Item item) {
        Cart cart = this.findCartByUser(username);
        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (cartItem == null) {
            cartItem = new CartItem(cart, item);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setPrice(item.getPrice() * cartItem.getQuantity());
        }
        return cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteCartItemById(id);
    }

    public void deleteCart(String username) {
        Cart cart = this.findCartByUser(username);
        cartItemRepository.deleteAllByCart(cart);
    }
}
