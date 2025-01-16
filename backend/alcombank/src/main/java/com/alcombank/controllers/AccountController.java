package com.alcombank.controllers;

import com.alcombank.models.Account;
import com.alcombank.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> transferDetailsRequest) {
        String senderCardNumber = (String) transferDetailsRequest.get("senderCardNumber");
        String recieverCardNumber = (String) transferDetailsRequest.get("recieverCardNumber");
        Float amount = (Float) transferDetailsRequest.get("amount");

        Optional<Account> senderAccountOpt = accountRepository.findByCardNumber(senderCardNumber);
        Optional<Account> receiverAccountOpt = accountRepository.findByCardNumber(recieverCardNumber);

        if (senderAccountOpt.isEmpty() || receiverAccountOpt.isEmpty()) {
            return ResponseEntity.status(404).body("One or both accounts not found");
        }

        Account senderAccount = senderAccountOpt.get();
        Account receiverAccount = receiverAccountOpt.get();

        if (senderAccount.getBalance() < amount) {
            return ResponseEntity.status(400).body("Insufficient funds");
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        return ResponseEntity.ok(senderAccount.getBalance());
    }


    @PostMapping("/updateCardDetails")
    public ResponseEntity<?> updateCardDetails(@RequestBody Map<String, Object> updateCardDetailsRequest) {
        Integer accountId = (Integer) updateCardDetailsRequest.get("accountId");
        String newCardNumber = (String) updateCardDetailsRequest.get("newCardNumber");
        String newCardExpireDate = (String) updateCardDetailsRequest.get("newCardExpireDate");

        if (accountId == null || (newCardNumber == null && newCardExpireDate == null)) {
            return ResponseEntity.status(400).body("Invalid request data");
        }

        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(404).body("Account not found");
        }

        Account account = optionalAccount.get();

        if (newCardNumber != null && !newCardNumber.isEmpty()) {
            account.setCardNumber(newCardNumber);
        }
        if (newCardExpireDate != null && !newCardExpireDate.isEmpty()) {
            account.setCardExpireDate(newCardExpireDate);
        }

        accountRepository.save(account);

        Map<String, String> response = new HashMap<>();
        response.put("cardNumber", newCardNumber);
        response.put("cardExpireDate", newCardExpireDate);

        return ResponseEntity.ok(response);
    }
}