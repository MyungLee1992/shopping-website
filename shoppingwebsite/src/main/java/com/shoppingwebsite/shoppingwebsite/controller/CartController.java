package com.shoppingwebsite.shoppingwebsite.controller;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.CartItem;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(Principal principal) {
        List<CartItem> cartItems = cartService.findCartItemsByUser(principal.getName());
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(Principal principal, @RequestBody Item item) {
        CartItem cartItem = cartService.addCartItem(principal.getName(), item);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartService.updateCartItem(cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable("id") Long id) {
        cartService.deleteCartItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Cart> deleteCart(Principal principal) {
        cartService.deleteCart(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
