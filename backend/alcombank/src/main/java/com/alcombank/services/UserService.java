package com.alcombank.services;

import com.alcombank.models.User;
import com.alcombank.models.Account;
import com.alcombank.repositories.UserRepository;
import com.alcombank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    public User loginUser(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean registerUser(String name, String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);

            Account newAccount = new Account();
            newAccount.setBalance(0.0f);
            newAccount.setCardNumber(null);
            newAccount.setExpireDate(null);

            newUser.setAccount(newAccount);  // Link user to account

            userRepository.save(newUser);

            return true;
        }
        return false;
    }
}