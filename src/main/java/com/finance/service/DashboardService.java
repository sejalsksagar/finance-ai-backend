package com.finance.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.repository.TransactionRepository;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository repo;

    public Map<String, Object> getDashboardSummary(int month, int year) {
        Map<String, Object> result = new HashMap<>();

        double expense = repo.getMonthlyExpense(month, year);
        double income = repo.getMonthlyIncome(month, year);

        result.put("totalExpense", expense);
        result.put("totalIncome", income);
        result.put("netSavings", income - expense);

        // Category-wise summary
        List<Object[]> categoryData = repo.getCategorySummary(month, year);
        Map<String, Double> categoryMap = new HashMap<>();

        for (Object[] row : categoryData) {
            categoryMap.put((String) row[0], ((Number) row[1]).doubleValue());
        }
        result.put("categorySummary", categoryMap);

        // Monthly trend
        List<Object[]> trendData = repo.getMonthlyTrend();
        Map<String, Double> trendMap = new LinkedHashMap<>();
        for (Object[] row : trendData) {
            trendMap.put((String) row[0], ((Number) row[1]).doubleValue());
        }
        result.put("trend", trendMap);

        return result;
    }
}