package com.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBankAccountId(Long bankAccountId);
}
