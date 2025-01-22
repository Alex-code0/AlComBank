package com.alcombank.repositories;

import com.alcombank.models.Credit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, int> {
    //TODO
}