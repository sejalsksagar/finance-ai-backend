package com.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping
    public ResponseEntity<?> getSummary(
        @RequestParam int month,
        @RequestParam int year
    ) {
        return ResponseEntity.ok(service.getDashboardSummary(month, year));
    }
}
