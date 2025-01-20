package com.alcombank.controllers;

import com.alcombank.models.User;
import com.alcombank.models.Account;
import com.alcombank.repositories.UserRepository;
import com.alcombank.repositories.AccountRepository;
import com.alcombank.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = userOptional.get();

        // Assuming passwords are hashed
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Account account = user.getAccount();
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found for the user");
        }

        String jwtToken = jwtService.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user.getName());
        response.put("token", jwtToken);
        response.put("account", account);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        User newUser = new User();
        newUser.setName(signupRequest.getName());
        newUser.setEmail(signupRequest.getEmail());
        // Use a secure hashing library for password storage
        newUser.setPassword(signupRequest.getPassword());

        Account newAccount = new Account();
        newAccount.setBalance(0.0f);
        accountRepository.save(newAccount);

        newUser.setAccount(newAccount);
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful");
    }
}