package com.example.budget.summary;

import java.math.BigDecimal;
import java.util.Map;

public class SummaryResponse {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Map<String, BigDecimal> expensesByCategory;

    public SummaryResponse(BigDecimal totalIncome, BigDecimal totalExpense, Map<String, BigDecimal> expensesByCategory){
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.expensesByCategory = expensesByCategory;
    }

    public BigDecimal getTotalIncome(){return totalIncome;}
    public BigDecimal getTotalExpense(){return totalExpense;}
    public Map<String, BigDecimal> getExpensesByCategory(){return expensesByCategory;}

}
