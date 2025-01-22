package com.alcombank.controllers;

import com.alcombank.models.Credit;
import com.alcombank.repositories.CreditRepository;

@RestController
@RequestMapping("/api")
public class CreditController {

    @Autowired
    private CreditRepository creditRepository;

    @PostMapping("/credit")
    public ResponseEntity<?> credit(@RequestBody credit creditRequest) {
        return ResponseEntity.ok()
    }
}