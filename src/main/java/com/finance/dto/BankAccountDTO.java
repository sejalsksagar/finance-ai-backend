package com.finance.dto;

import lombok.Data;

@Data
public class BankAccountDTO {

    private Long id;
    private String bankName;
    private String accountNumber;
    private String accountType;
    private Double balance;
}
