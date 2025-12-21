package com.finance.dto;

import lombok.Data;

@Data
public class InsightDTO {

    private Long id;
    private String type; // warning | info | success
    private String title;
    private String message;
}

