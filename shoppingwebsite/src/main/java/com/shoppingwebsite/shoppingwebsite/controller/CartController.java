package com.shoppingwebsite.shoppingwebsite.controller;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {
        Cart cart = cartService.findCartByUser(principal.getName());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCartItem(Principal principal, @RequestParam Long itemId) {
        Cart cart = cartService.addCartItem(principal.getName(), itemId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(Principal principal, @RequestBody Cart cart) {
        Cart updateCart = cartService.updateCart(cart);
        return new ResponseEntity<>(updateCart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Cart> deleteItem(Principal principal, @PathVariable("id") Long id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
