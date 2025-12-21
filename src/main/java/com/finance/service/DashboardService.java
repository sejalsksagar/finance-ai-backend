package com.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.finance.dto.CategoryBreakdownDTO;
import com.finance.dto.DashboardResponse;
import com.finance.dto.InsightDTO;
import com.finance.dto.MonthlyTrendDTO;
import com.finance.dto.TransactionDTO;
import com.finance.entity.TransactionType;
import com.finance.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final TransactionRepository transactionRepository;
    
    private final WebClient webClient;

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
    
    public List<TransactionDTO> getUserTransactionsForAI(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    ) {

        // Same defaults as dashboard
        if (startDate == null) {
            startDate = LocalDate.of(1970, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        return transactionRepository
                .findTransactionsForAI(userId, startDate, endDate)
                .stream()
                .map(tx -> new TransactionDTO(
                        tx.getId(),
                        tx.getAmount(),
                        tx.getCategory().name(),
                        tx.getType().name(),
                        tx.getDescription(),
                        tx.getDate()
                ))
                .collect(Collectors.toList());
    }
    
    public List<InsightDTO> fetchInsights(List<TransactionDTO> transactions) {
        return webClient.post()
                .uri("http://localhost:8001/insights")
                .bodyValue(transactions)
                .retrieve()
                .bodyToFlux(InsightDTO.class)
                .collectList()
                .block();
    }
}
