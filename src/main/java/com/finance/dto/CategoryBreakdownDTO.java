package com.finance.dto;

import com.finance.entity.TransactionCategory;

import lombok.Getter;

@Getter
public class CategoryBreakdownDTO {

    private final TransactionCategory category;
    private final double amount;

    //  Native SQL constructor
    public CategoryBreakdownDTO(String category, double amount) {
        this.category = TransactionCategory.valueOf(category);
        this.amount = amount;
    }
}
