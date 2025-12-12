package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.entity.BankAccount;
import com.finance.entity.Transaction;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    @Autowired
    private BankAccountRepository accountRepo;

    public Transaction addTransaction(Long accountId, Transaction tx) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        tx.setBankAccount(account);

        // Update account balance
        if (tx.getType().equalsIgnoreCase("DEBIT")) {
            account.setBalance(account.getBalance() - tx.getAmount());
        } else {
            account.setBalance(account.getBalance() + tx.getAmount());
        }

        accountRepo.save(account);
        return repo.save(tx);
    }
    
    public List<Transaction> getTransactions(Long accountId) {
        return repo.findByBankAccountId(accountId);
    }
}
