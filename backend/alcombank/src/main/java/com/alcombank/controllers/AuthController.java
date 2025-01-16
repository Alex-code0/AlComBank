package com.alcombank.controllers;

import com.alcombank.models.User;
import com.alcombank.models.Account;
import com.alcombank.repositories.UserRepository;
import com.alcombank.repositories.AccountRepository;
import com.alcombank.services.jwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private jwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        Account account = user.getAccount();
        if (account == null) {
            return ResponseEntity.status(404).body("Account not found for the user");
        }

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("accountData", account);
        response.put("jwtToken", jwtToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already in use");
        }

        User newUser = new User();
        newUser.setName(signupRequest.getName());
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(signupRequest.getPassword());

        Account newAccount = new Account();
        newAccount.setBalance(0.0f);
        newAccount.setCardNumber(null);
        newAccount.setCardExpireDate(null);

        accountRepository.save(newAccount);
        newUser.setAccount(newAccount);
        userRepository.save(newUser);

        return ResponseEntity.ok("Signup successful");
    }
}