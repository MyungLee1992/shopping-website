package com.shoppingwebsite.shoppingwebsite.controller;

import com.shoppingwebsite.shoppingwebsite.model.Cart;
import com.shoppingwebsite.shoppingwebsite.model.Item;
import com.shoppingwebsite.shoppingwebsite.service.CartService;
import com.shoppingwebsite.shoppingwebsite.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final ItemService itemService;
    private final CartService cartService;

    public CartController(ItemService itemService, CartService cartService) {
        this.itemService = itemService;
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
    public ResponseEntity<Item> updateItem(Principal principal, @RequestBody Item item) {
        Item updateItem = itemService.updateItem(item);
        return new ResponseEntity<>(updateItem, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Item> deleteItem(Principal principal, @PathVariable("id") Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
