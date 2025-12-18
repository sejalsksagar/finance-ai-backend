package com.finance.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dto.CategoryBreakdownDTO;
import com.finance.dto.DashboardResponse;
import com.finance.dto.MonthlyTrendDTO;
import com.finance.entity.TransactionType;
import com.finance.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final TransactionRepository transactionRepository;

    public DashboardResponse getDashboardSummary(
            Long userId,
            LocalDate startDate,
            LocalDate endDate) {

        // Default date range 
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        // ---------- Summary ----------
        Double income = transactionRepository
                .sumAmountByTypeAndUserAndDateRange(
                        TransactionType.INCOME.name(), userId, startDate, endDate);

        Double expense = transactionRepository
                .sumAmountByTypeAndUserAndDateRange(
                        TransactionType.EXPENSE.name(), userId, startDate, endDate);

        long transactionCount =
                transactionRepository.countByUserAndDateRange(
                        userId, startDate, endDate);

        double totalIncome = income != null ? income : 0.0;
        double totalExpense = expense != null ? expense : 0.0;

        DashboardResponse.Summary summary =
                new DashboardResponse.Summary(
                        totalIncome,
                        totalExpense,
                        totalIncome - totalExpense,
                        transactionCount
                );

        // ---------- Category Breakdown ----------
        List<CategoryBreakdownDTO> categoryBreakdown =
                transactionRepository.findExpenseBreakdownByCategory(
                        userId, startDate, endDate);

        // ---------- Monthly Trend ----------
        List<MonthlyTrendDTO> monthlyTrend =
                transactionRepository.findMonthlyIncomeExpenseTrend(
                        userId, startDate, endDate);

        // ---------- Final Response ----------
        return new DashboardResponse(
                summary,
                categoryBreakdown,
                monthlyTrend
        );
    }
}
