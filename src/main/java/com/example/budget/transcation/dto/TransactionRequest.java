package com.example.budget.transcation.dto;

import com.example.budget.transcation.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {

    public TransactionRequest() {}

    public TransactionRequest(Long accountId, BigDecimal amount, TransactionType type, String category, LocalDate date, String description) {
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    @NotNull(message = "Konto jest wymagane")
    private Long accountId;
    @NotNull @Positive
    private BigDecimal amount;
    @NotNull
    private LocalDate date;
    @NotNull
    private TransactionType type;
    @NotBlank
    private String category;

    // jedyne bez walidacji - jest opcjonalne
    private String description;

    // gettery dla Jacksona - deserializacja: JSON -> obiekt Java
    public Long getAccountId(){return accountId;}
    public BigDecimal getAmount(){return amount;}
    public LocalDate getDate(){return date;}
    public TransactionType getType(){return type;}
    public String getCategory(){return category;}
    public String getDescription(){return description;}


}
