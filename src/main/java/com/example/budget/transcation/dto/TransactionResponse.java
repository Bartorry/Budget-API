package com.example.budget.transcation.dto;

import com.example.budget.transcation.TransactionType;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionResponse {

    // co zwracamy klientowi jako informacje o transkacji
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate date;
    private String category;
    private String description;


    public TransactionResponse(Long id, Long accountId, BigDecimal amount, TransactionType type, LocalDate date, String category, String description){
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.category = category;
        this.description = description;

    }


    // gettery dla Jacksona - serializacja: obiekt Javy -> JSON
    public Long getId(){return id;}
    public Long getAccountId(){return accountId;}
    public TransactionType getType(){return type;}
    public BigDecimal getAmount(){return amount;}
    public LocalDate getDate(){return date;}
    public String getCategory(){return category;}
    public String getDescription(){return description;}

}
