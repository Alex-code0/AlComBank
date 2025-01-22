package com.alcombank.models;

import com.alcombank.models.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCredit")
    private int idCredit;

    @Column(name = "SumaCredit", nullable = false)
    private int sumaCredit;

    @Column(name = "PerioadaCreditare", nullable = false)
    private int perioadaCreditare;

    @Column(name = "RataAnualaCredit", nullable = false)
    private int rataAnualaCredit;

    @Column(name = "DataAcreditarii")
    private LocalDate dataAcreditarii;

    @OneToOne(mappedBy = "credit", cascade = CascadeType.ALL)
    private Account account;

    //Getters
    public int getIdCredit() { return idCredit; }
    public int getSumaCredit() { return sumaCredit; }
    public int getPerioadaCreditare() { return perioadaCreditare; }
    public int getRataAnualaCredit() { return rataAnualaCredit; }
    public LocalDate getDataAcreditarii() { return dataAcreditarii; }

    //Setters
    public void setIdCredit(int idCredit) { this.idCredit = idCredit; }
    public void setSumaCredit(int sumaCredit) { this.sumaCredit = sumaCredit; }
    public void setPerioadaCreditare(int perioadaCreditare) { this.perioadaCreditare = perioadaCreditare; }
    public void setRataAnualaCredit(int rataAnualaCredit) { this.rataAnualaCredit = rataAnualaCredit; }
    public void setDataAcreditarii(LocalDate dataAcreditarii) { this.dataAcreditarii = dataAcreditarii; }
}