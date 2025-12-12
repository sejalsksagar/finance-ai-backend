package com.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.entity.BankAccount;
import com.finance.entity.User;
import com.finance.repository.BankAccountRepository;
import com.finance.repository.UserRepository;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository repo;

    @Autowired
    private UserRepository userRepo;

    public BankAccount addAccount(Long userId, BankAccount account) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        account.setUser(user);
        return repo.save(account);
    }

    public List<BankAccount> getUserAccounts(Long userId) {
        return repo.findByUserId(userId);
    }
}
