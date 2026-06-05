package com.example.budget.account.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountRequest {

    @NotBlank(message = "Nazwa konta nie może być pusta")
    private String name;

    public AccountRequest(){}

    public AccountRequest(String name) {
        this.name = name;
    }

    public String getName() {return name;}
}
