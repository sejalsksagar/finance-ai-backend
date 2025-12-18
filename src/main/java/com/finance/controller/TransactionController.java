package com.finance.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.finance.dto.CreateTransactionRequest;
import com.finance.dto.TransactionDTO;
import com.finance.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /* =========================================================
       Create Transaction
       ========================================================= */

    @PostMapping("/accounts/{accountId}")
    public ResponseEntity<TransactionDTO> createTransaction(
            @PathVariable Long accountId,
            @Valid @RequestBody CreateTransactionRequest request) {

        TransactionDTO response =
                transactionService.createTransaction(accountId, request);

        return ResponseEntity
                .created(URI.create("/api/transactions/" + response.getId()))
                .body(response);
    }

    /* =========================================================
       Fetch Transactions
       ========================================================= */

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(
                transactionService.getTransactions(accountId)
        );
    }
}
