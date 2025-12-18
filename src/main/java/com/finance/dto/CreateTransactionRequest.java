package com.finance.dto;

import com.finance.entity.TransactionCategory;
import com.finance.entity.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTransactionRequest {

    @NotNull
    private Double amount;

    @NotNull
    private TransactionType type;

    @NotNull
    private TransactionCategory category;

    private String description;
}
