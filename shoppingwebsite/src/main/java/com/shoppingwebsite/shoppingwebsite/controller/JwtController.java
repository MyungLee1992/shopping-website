package com.shoppingwebsite.shoppingwebsite.controller;

import java.util.Objects;

import com.shoppingwebsite.shoppingwebsite.configuration.JwtResponse;
import com.shoppingwebsite.shoppingwebsite.configuration.JwtTokenUtil;
import com.shoppingwebsite.shoppingwebsite.model.User;
import com.shoppingwebsite.shoppingwebsite.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}