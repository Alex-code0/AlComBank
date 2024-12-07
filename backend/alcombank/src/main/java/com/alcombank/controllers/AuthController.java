package com.alcombank.controllers;

import com.alcombank.models.User;
import com.alcombank.models.Account;
import com.alcombank.services.UserService;
import com.alcombank.services.AccountService;
import com.alcombank.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            Account account = accountService.getAccountDetails(user.getAccount().getId());
            
            if (account == null) {
                return ResponseEntity.status(404).body("Account details not found");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("account", account);
            response.put("token", jwtService.generateToken(user.getId()));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User registerRequest) {
        boolean isRegistered = userService.registerUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());

        if (isRegistered) {
            return ResponseEntity.ok("Registration successful!");
        } else {
            return ResponseEntity.status(409).body("User already exists");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> transferData) {
        String senderCardNumber = (String) transferData.get("sender");
        String recipientCardNumber = (String) transferData.get("recipient");
        Float transferAmount = Float.parseFloat(transferData.get("transferAmount").toString());

        Optional<Account> senderAccountOpt = accountService.getAccountByCardNumber(senderCardNumber);
        Optional<Account> recipientAccountOpt = accountService.getAccountByCardNumber(recipientCardNumber);

        if (recipientAccountOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Recipient account not found");
        }

        Account senderAccount = senderAccountOpt.get();
        Account recipientAccount = recipientAccountOpt.get();

        if(senderAccount.getBalance() < transferAmount) {
            return ResponseEntity.badRequest().body("Insufficient funds.");
        }

        senderAccount.setBalance(senderAccount.getBalance() - transferAmount);
        recipientAccount.setBalance(recipientAccount.getBalance() + transferAmount);

        accountService.updateAccount(senderAccount);
        accountService.updateAccount(recipientAccount);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Transfer succseful.");
        response.put("senderBalance", senderAccount.getBalance());

        return ResponseEntity.ok(response);
    }
}