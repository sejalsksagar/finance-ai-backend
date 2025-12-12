package com.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.entity.BankAccount;
import com.finance.request.CreateBankAccountRequest;
import com.finance.service.BankAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService service;

    @PostMapping("/{userId}")
    public ResponseEntity<BankAccount> addAccount(
            @PathVariable Long userId,
            @Valid @RequestBody CreateBankAccountRequest request) {

        BankAccount account = new BankAccount();
        account.setBankName(request.getBankName());
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());

        return ResponseEntity.ok(service.addAccount(userId, account));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<BankAccount>> getAccounts(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserAccounts(userId));
    }
}
