package com.finance.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Summary summary;
    private List<CategoryBreakdownDTO> categoryBreakdown;
    private List<MonthlyTrendDTO> monthlyTrend;

    /* ================= Summary ================= */

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        private double totalIncome;
        private double totalExpense;
        private double netSavings;
        private long transactionCount;
    }
}
