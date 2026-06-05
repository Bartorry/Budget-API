package com.example.budget.transcation;

import com.example.budget.transcation.dto.TransactionRequest;
import com.example.budget.transcation.dto.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {


    private final TransactionService service;

    // wstrzykniety TransactionService
    public TransactionController(TransactionService service){
        this.service = service;
    }

    // metody kontrolera

    // post - tworzenie transakcji zwraca
    @PostMapping
    public ResponseEntity<TransactionResponse> post(@Valid @RequestBody TransactionRequest body){
        TransactionResponse response  = service.createTransaction(body);
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteTransaction(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping
    public List<TransactionResponse> getAll(
            @RequestParam (required = false) Long accountId,
            @RequestParam (required = false) String category,
            @RequestParam (required = false) LocalDate from,
            @RequestParam (required = false) LocalDate to){

        return service.getTransactions(accountId, category, from, to);
    }








}
