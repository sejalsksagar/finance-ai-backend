package com.finance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dto.DashboardResponse;
import com.finance.dto.InsightDTO;
import com.finance.dto.TransactionDTO;
import com.finance.security.UserPrincipal;
import com.finance.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam(required = false)
            LocalDate startDate,
            @RequestParam(required = false)
            LocalDate endDate
    ) {
        return ResponseEntity.ok(
                service.getDashboardSummary(
                        user.getId(), startDate, endDate
                )
        );
    }
    
    @GetMapping("/insights")
    public List<InsightDTO> getInsights(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {

        List<TransactionDTO> transactions =
                service.getUserTransactionsForAI(
                        user.getId(),
                        startDate,
                        endDate
                );

        return service.fetchInsights(transactions);
    }


}

