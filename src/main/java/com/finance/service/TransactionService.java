package com.finance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.dto.TransactionDTO;
import com.finance.dto.CreateTransactionRequest;
import com.finance.entity.BankAccount;
import com.finance.entity.Transaction;
import com.finance.entity.TransactionType;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    /* =========================================================
       Create Transaction (Command DTO → Entity → Response DTO)
       ========================================================= */
    @Transactional
    public TransactionDTO createTransaction(
            Long accountId,
            CreateTransactionRequest request) {

        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setDate(LocalDate.now());
        transaction.setBankAccount(account);

        applyBalanceUpdate(account, transaction);

        Transaction saved = transactionRepository.save(transaction);

        return mapToDto(saved);
    }

    /* =========================================================
       Fetch Transactions
       ========================================================= */
    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactions(Long accountId) {
        return transactionRepository.findByBankAccountId(accountId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /* =========================================================
       Business Logic
       ========================================================= */
    private void applyBalanceUpdate(BankAccount account, Transaction transaction) {

        if (transaction.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else if (transaction.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            throw new IllegalStateException("Unsupported transaction type");
        }
    }

    /* =========================================================
       Entity → DTO Mapping
       ========================================================= */
    private TransactionDTO mapToDto(Transaction tx) {

        TransactionDTO dto = new TransactionDTO();
        dto.setId(tx.getId());
        dto.setAmount(tx.getAmount());
        dto.setCategory(tx.getCategory().name());
        dto.setType(tx.getType().name());
        dto.setDescription(tx.getDescription());
        dto.setDate(tx.getDate());

        return dto;
    }
}
