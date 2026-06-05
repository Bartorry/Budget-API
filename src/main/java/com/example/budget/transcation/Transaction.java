package com.example.budget.transcation;

import com.example.budget.account.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate date;

    // @Enumerated - aby Hibernate nie przechowywal enum jako 0 i 1 tylko String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column
    private String description;

    // konstruktor bezargumentowy dla Hibernate - poprzez refleksje wpisuje w insancje klasy dane z bazy danych
    protected Transaction(){}

    // konstruktor public dla nas do wypelnienia pol
    public Transaction(Account account, BigDecimal amount, String category, LocalDate date, TransactionType type, String description){
        this.account = account;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
        this.description = description;
    }



    // nie ma seterow bo nigdy walsciwosci transkacji juz sie nie zmienia

    // gettery dla wszystkich pol
    public Long getId(){return id;}

    public Account getAccount(){return account;}

    public BigDecimal getAmount(){return amount;}

    public String getCategory(){return category;}

    public LocalDate getDate(){return date;}

    public TransactionType getType(){return type;}

    public String getDescription(){return description;}








}