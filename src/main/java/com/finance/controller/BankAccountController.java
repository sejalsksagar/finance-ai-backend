package com.finance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.finance.dto.CreateBankAccountRequest;
import com.finance.entity.BankAccount;
import com.finance.security.UserPrincipal;
import com.finance.service.BankAccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService service;

    /* ==============================
       Create Account
       ============================== */

    @PostMapping
    public ResponseEntity<BankAccount> addAccount(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody CreateBankAccountRequest request) {

        BankAccount account = new BankAccount();
        account.setBankName(request.getBankName());
        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());

        return ResponseEntity.ok(
                service.addAccount(user.getId(), account)
        );
    }

    /* ==============================
       Get All User Accounts
       ============================== */

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAccounts(
            @AuthenticationPrincipal UserPrincipal user) {

        return ResponseEntity.ok(
                service.getUserAccounts(user.getId())
        );
    }
}
