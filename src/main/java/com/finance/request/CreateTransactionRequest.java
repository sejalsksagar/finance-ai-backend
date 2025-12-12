package com.finance.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTransactionRequest {

    @NotNull
    private Double amount;

    @NotBlank
    private String category;

    @NotBlank
    private String type; // DEBIT or CREDIT

    private String description;
}
