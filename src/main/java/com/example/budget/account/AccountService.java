package com.example.budget.account;

import com.example.budget.account.dto.AccountRequest;
import com.example.budget.account.dto.AccountResponse;
import com.example.budget.exception.ResourceAlreadyExistsException;
import com.example.budget.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    // jedna instancja tej @Service tworzona jest przez Spring
    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    // tworzymy metode zwracajaca liste AccountResponse obiektow
    // uzywamy stream dla kazdego obiektu Account tworzymy Account Reponse i
    // do konstruktora podajemy pola z obiektu Account

    public List<AccountResponse> getAllAccounts(){

        return repository.findAll().stream().map(account -> new AccountResponse(
                account.getId(),
                account.getName(),
                account.getBalance()
        )).toList();
    }

    // metoda sprawdzajaca czy istnieje dane konto w bazie
    public AccountResponse getAccountById(Long id){
        Account account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Konto nie istnieje"));
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getBalance()
        );
    }


    // DTO - kontroler przekazuje obiekt Request bezposrednio bez
    // rozpakowywania - inaczej trzeba wrzucac do konstruktora wszystkie argumenty
    public AccountResponse createAccount(AccountRequest request){

        //sprawdzenie czy konto juz istnieje w bazie
        if (repository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Konto o nazwie '" + request.getName() + "' już istnieje");
        }

        Account account = new Account(request.getName());

        /*potrzebujemy saved poniewaz baza dopiero po zapisie
         * nada polom wartosci - account.getId zwroci null*/
        Account saved = repository.save(account);
        return new AccountResponse(
                saved.getId(),
                saved.getName(),
                saved.getBalance()
        );
    }

    public void deleteAccount(Long id){
            Account account = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Konto o podanym ID nie istnieje\n"));
        repository.delete(account);
    }

    public Account findAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Konto nie istnieje"));
    }

    // saveAccount - metoda uzyta w TransactionService
    public void saveAccount(Account account){
        repository.save(account);
    }



}
