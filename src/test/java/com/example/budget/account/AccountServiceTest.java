package com.example.budget.account;

import com.example.budget.account.dto.AccountRequest;
import com.example.budget.account.dto.AccountResponse;
import com.example.budget.exception.ResourceAlreadyExistsException;
import com.example.budget.exception.ResourceNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    @Test
    void createAccount_shouldReturnAccountResponse(){

        // GIVEN - przygotowanie danych
        AccountRequest request = new AccountRequest("Konto główne");
        when(repository.existsByName("Konto główne")).thenReturn(false);
        when(repository.save(any())).thenReturn(new Account("Konto główne"));

        // WHEN - wywołanie testowej metody
        AccountResponse response = service.createAccount(request);

        // THEN - sprawdzenie wyników
        assertThat(response.getName()).isEqualTo("Konto główne");

    }

    @Test
    void createAccount_shouldReturnResourceAlreadyExistsException(){

        AccountRequest request = new AccountRequest("Konto główne");
        when(repository.existsByName("Konto główne")).thenReturn(true);


        assertThatThrownBy(() -> service.createAccount(request))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void getAccountById_shouldReturnAccountResponse(){

        when(repository.findById(1L)).thenReturn(Optional.of(new Account("Konto główne")));
        AccountResponse response = service.getAccountById(1L);

        assertThat(response.getName()).isEqualTo("Konto główne");
    }

    @Test
    void getAccountById_shouldReturnResourceNotFoundException(){

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getAccountById(1L))
                .isInstanceOf(ResourceNotFoundException.class);

    }
}
