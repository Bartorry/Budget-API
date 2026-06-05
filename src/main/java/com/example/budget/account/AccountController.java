package com.example.budget.account;
import java.util.List;

import com.example.budget.account.dto.AccountRequest;
import com.example.budget.account.dto.AccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service){
        this.service = service;
    }

    // metody kontrolera

    // adnotacja mowi Springowi za co odpowiada dana klasa
    @GetMapping
    public List<AccountResponse> getAllAccounts(){
        return service.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id){
        return service.getAccountById(id);
    }

    // ResponseEntity - opakowanie pozwalajace kontrolowac kod HTTP odpowiedzi
    @PostMapping
    public ResponseEntity<AccountResponse> post(@Valid @RequestBody AccountRequest name){
        AccountResponse response =  service.createAccount(name);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.deleteAccount(id);
    }



}
