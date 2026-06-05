package com.example.budget.summary;

import com.example.budget.transcation.TransactionRepository;
import com.example.budget.transcation.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SummaryService {

    private final TransactionRepository repository;

    public SummaryService(TransactionRepository repository){
        this.repository = repository;
    }

    public SummaryResponse getSummary(){

        BigDecimal totalIncome = repository.sumByType(TransactionType.INCOME);
        BigDecimal totalExpense = repository.sumByType(TransactionType.EXPENSE);
        List<Object[]> expenseByCategory = repository.sumByCategory();

        // Spring nie wiem jak spakowac wiec podaje dwie surowe tablice ( talice obiekty)
        // chcemy je zmapowac na klucz-wartosc

        Map<String, BigDecimal> map = new HashMap<>();
        for (Object[] row: expenseByCategory){
            String category = (String) row[0];
            BigDecimal expense = (BigDecimal)  row[1];
            map.put(category, expense);
        }

        return new SummaryResponse(totalIncome, totalExpense, map);
    }














}
