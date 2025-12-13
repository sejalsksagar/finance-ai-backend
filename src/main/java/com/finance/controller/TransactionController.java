package com.finance.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.entity.Transaction;
import com.finance.request.CreateTransactionRequest;
import com.finance.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/{accountId}")
    public ResponseEntity<Transaction> addTransaction(
            @PathVariable Long accountId,
            @Valid @RequestBody CreateTransactionRequest request) {

        Transaction tx = new Transaction();
        tx.setAmount(request.getAmount());
        tx.setCategory(request.getCategory());
        tx.setType(request.getType());
        tx.setDescription(request.getDescription());
        tx.setDate(LocalDate.now());

        return ResponseEntity.ok(service.addTransaction(accountId, tx));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.getTransactions(accountId));
    }
}
