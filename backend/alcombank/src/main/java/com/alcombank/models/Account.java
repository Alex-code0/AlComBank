package com.alcombank.models;

import com.alcombank.models.Credit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    private Integer accountId;

    @Column(name = "Balance", nullable = false)
    private Float balance = 0.0f;

    @Column(name = "CardNumber", length = 16)
    private String cardNumber;

    @Column(name = "CardExpireDate", length = 4)
    private String cardExpireDate;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IdCredit", referencedColumnName = "IdCredit")
    private Credit credit;


    //Getters
    public Integer getAccountId() { return accountId; }
    public Float getBalance() { return balance; }
    public String getCardNumber() { return cardNumber; }
    public String getCardExpireDate() { return cardExpireDate; }
    public Credit getCredit() { return credit; }

    //Setters
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
    public void setBalance(Float balance) { this.balance = balance; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public void setCardExpireDate(String cardExpireDate) { this.cardExpireDate = cardExpireDate; }
    public void setCredit(Credit credit) { this.credit = credit; }
}