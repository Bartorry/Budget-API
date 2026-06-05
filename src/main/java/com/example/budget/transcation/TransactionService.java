package com.example.budget.transcation;

import com.example.budget.account.Account;
import com.example.budget.account.AccountService;
import com.example.budget.exception.ResourceNotFoundException;
import com.example.budget.transcation.dto.TransactionRequest;
import com.example.budget.transcation.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountService accountService, TransactionRepository transactionRepository){
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse createTransaction(TransactionRequest request){
        Account account = accountService.findAccountById(request.getAccountId());
        Transaction transaction = new Transaction(account, request.getAmount(), request.getCategory(), request.getDate(), request.getType(), request.getDescription());

        // ZMIANA SALDA

        // jesli debit
        if (transaction.getType() == TransactionType.EXPENSE){
            account.debit(transaction.getAmount());
        }

        // jesli credit
        else{
            account.credit(transaction.getAmount());
        }

        // transakcje zapisujemy dopiero po zmianie salda -> bezpieczenstwo
        Transaction transactionSaved = transactionRepository.save(transaction);

        accountService.saveAccount(account);


        return new TransactionResponse(transactionSaved.getId(), transactionSaved.getAccount().getId(), transactionSaved.getAmount(),  transactionSaved.getType(),transactionSaved.getDate(), transactionSaved.getCategory(), transactionSaved.getDescription());

    }





    // zwracamy void bo konwencja REST jest taka, że zwracane jest 204 No Content
    // czyli pusty response bez body
    // 204, czyli ok , ale nie ma co zwracać
    public void deleteTransaction(Long id){

        // pobieramy transakcje na podstawie id
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Transakcja o podanym ID nie istnieje "));

        // pobieramy konto na podstawie powiazanej z nim transakcji
        Account account = transaction.getAccount();

        // jesli to byla INCOME - odejmij
        if(transaction.getType() == TransactionType.INCOME){
            account.debit(transaction.getAmount());
        }

        // jesli to byla EXPENSE - dodaj
        else{
            account.credit(transaction.getAmount());
        }

        // zapisujemy konto
        // AccountService wstrzyknięte tutaj ma metodę zapisywania konta
        accountService.saveAccount(account);

        // usuwamy wiersz z tabeli transkacji
        transactionRepository.delete(transaction);
    }


    // metoda pomocnicza dla filtrowania (oszczedza bardzo duzo miejsca w kodzie - 4x to samo)

    private TransactionResponse toResponse(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getCategory(),
                transaction.getDescription()
        );
    }

    // tworzymy 4 przypadki dla wyszukiwania z filtrami lub bez
    // accountId - flitruj po koncie
    // category - po kategorii
    // po dacie
    // else - nie podano opcjonalnych filtrów
    public List<TransactionResponse> getTransactions(Long accountId, String category, LocalDate from, LocalDate to){
        if (accountId != null){
            return transactionRepository.findByAccountId(accountId).stream().map(transaction -> toResponse(transaction)).toList();
        }
        else if (category != null) {
            return transactionRepository.findByCategory(category).stream().map(transaction -> toResponse(transaction)).toList();
        }
        else if (from != null && to != null) {
            return transactionRepository.findByDateBetween(from, to).stream().map(transaction -> toResponse(transaction)).toList();
        }
        else{
            return transactionRepository.findAll().stream().map(transaction -> toResponse(transaction)).toList();
        }
    }



}
