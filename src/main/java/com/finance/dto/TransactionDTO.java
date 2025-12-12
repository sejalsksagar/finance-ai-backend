package com.finance.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDTO {

    private Long id;
    private Double amount;
    private String category;
    private String type;
    private String description;
    private LocalDate transactionDate;
}
