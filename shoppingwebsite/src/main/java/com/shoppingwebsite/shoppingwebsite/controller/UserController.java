package com.shoppingwebsite.shoppingwebsite.controller;

import com.shoppingwebsite.shoppingwebsite.model.User;
import com.shoppingwebsite.shoppingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        return ResponseEntity.ok(currentUser);
    }


}