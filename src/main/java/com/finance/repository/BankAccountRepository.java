package com.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByUserId(Long userId);
}
