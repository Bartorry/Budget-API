package com.example.budget.transcation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    // dzięki Spring JPA Data wystarzczą proste metody:

    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByCategory(String category);
    List<Transaction> findByDateBetween(LocalDate from, LocalDate to);

    // metody do summary
    // JPQL - operuje na klasach Java zamiast na tabelach
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
    BigDecimal sumByType(@Param("type") TransactionType type);

    // zwrócenie sumy wydatkow wg kategorii
    @Query("Select t.category, SUM(t.amount) FROM Transaction t WHERE t.type='EXPENSE' GROUP BY t.category")
    List<Object[]> sumByCategory();


}

