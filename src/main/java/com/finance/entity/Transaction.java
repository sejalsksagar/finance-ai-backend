package com.finance.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
	    name = "transactions",
	    indexes = {
	        @Index(name = "idx_transaction_bank_type_date", columnList = "bank_account_id, type, date"),
	        @Index(name = "idx_transaction_bank_category", columnList = "bank_account_id, category")
	    }
	)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "bankAccount")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @JsonIgnore
    private BankAccount bankAccount;
}
