package com.example.budget.account;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    // konstruktor bezargumentowy - protected aby mogl go uzyc tylko Hibernate
    protected Account(){}

    // public - uzywam go w AccountService
    public Account(String name){
        this.name = name;
        this.balance = BigDecimal.ZERO;
    }


    // DEBIT , CREDIT

    public void credit(BigDecimal amount){
        // BigDecimal niemutowalny - dlatego musze stworzyc nowy obiekt
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount){
        this.balance = this.balance.subtract(amount);
    }


    public Long getId(){return id;}

    public BigDecimal getBalance(){return balance;}

    public String getName(){return name;}
}
