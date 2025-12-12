package com.finance.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBankAccountRequest {
    @NotBlank
    private String bankName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountType;  // SAVINGS, CURRENT, CREDIT_CARD

    @NotNull
    private Double balance;
}