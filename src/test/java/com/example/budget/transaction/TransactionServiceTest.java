package com.example.budget.transaction;

import com.example.budget.account.Account;
import com.example.budget.account.AccountService;
import com.example.budget.transcation.Transaction;
import com.example.budget.transcation.TransactionRepository;
import com.example.budget.transcation.TransactionService;
import com.example.budget.transcation.TransactionType;
import com.example.budget.transcation.dto.TransactionRequest;
import com.example.budget.transcation.dto.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createTransaction_shouldReturnAccountResponse(){

        // 1. Request od klienta
        TransactionRequest request = new TransactionRequest(
                1L, new BigDecimal("50"), TransactionType.EXPENSE, "Jedzenie", LocalDate.now(), "Obiad"
        );

        // 2. Konto które zwróci mock
        Account account = new Account("Konto główne");


        Transaction savedTransaction = new Transaction(
                account, new BigDecimal("50"), "Jedzenie", LocalDate.now(), TransactionType.EXPENSE, "Obiad"
        );



        // 3. Skonfiguruj mocki
        when(accountService.findAccountById(1L)).thenReturn(account);
        when(transactionRepository.save(any())).thenReturn(savedTransaction);

        // WHEN
        TransactionResponse response = transactionService.createTransaction(request);


        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getAmount()).isEqualByComparingTo(new BigDecimal("50"));


    }

    @Test
    void deleteTransaction_shouldReturnAccountResponse(){


        Account account = new Account("Konto główne");

        Transaction transaction = new Transaction(
                account, new BigDecimal("50"), "Jedzenie", LocalDate.now(), TransactionType.EXPENSE, "Obiad"
        );

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        transactionService.deleteTransaction(1L);


        verify(accountService).saveAccount(account);
        verify(transactionRepository).delete(transaction);




    }
}
