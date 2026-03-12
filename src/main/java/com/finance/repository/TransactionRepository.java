package com.finance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finance.dto.CategoryBreakdownDTO;
import com.finance.dto.MonthlyTrendDTO;
import com.finance.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /* =========================================================
       Basic Queries
       ========================================================= */

    List<Transaction> findByBankAccountId(Long bankAccountId);
    List<Transaction> findByBankAccountUserIdOrderByDateDesc(Long userId); //transaction.bankAccount.user.id

    /* =========================================================
       Transaction Count (User Scoped)
       ========================================================= */

    @Query(
    	    value = """
    	        SELECT COUNT(*)
    	        FROM transactions t
    	        JOIN bank_account b ON t.bank_account_id = b.id
    	        WHERE b.user_id = :userId
    	          AND t.date BETWEEN :startDate AND :endDate
    	    """,
    	    nativeQuery = true
    	)
    	long countByUserAndDateRange(
    	    @Param("userId") Long userId,
    	    @Param("startDate") LocalDate startDate,
    	    @Param("endDate") LocalDate endDate
    	);


    /* =========================================================
       Summary Aggregations
       ========================================================= */

    @Query(
    	    value = """
    	        SELECT COALESCE(SUM(t.amount), 0)
    	        FROM transactions t
    	        JOIN bank_account b ON t.bank_account_id = b.id
    	        WHERE t.type = :type
    	          AND b.user_id = :userId
    	          AND t.date BETWEEN :startDate AND :endDate
    	    """,
    	    nativeQuery = true
    	)
    	Double sumAmountByTypeAndUserAndDateRange(
    	    @Param("type") String type,   // "INCOME" / "EXPENSE"
    	    @Param("userId") Long userId,
    	    @Param("startDate") LocalDate startDate,
    	    @Param("endDate") LocalDate endDate
    	);


    /* =========================================================
       Category Breakdown (Expense Only)
       ========================================================= */

    @Query(
    	    value = """
    	        SELECT 
    	            t.category AS category,
    	            SUM(t.amount) AS totalAmount
    	        FROM transactions t
    	        JOIN bank_account b ON t.bank_account_id = b.id
    	        WHERE t.type = 'EXPENSE'
    	          AND b.user_id = :userId
    	          AND t.date BETWEEN :startDate AND :endDate
    	        GROUP BY t.category
    	        ORDER BY totalAmount DESC
    	    """,
    	    nativeQuery = true
    	)
    	List<CategoryBreakdownDTO> findExpenseBreakdownByCategory(
    	    @Param("userId") Long userId,
    	    @Param("startDate") LocalDate startDate,
    	    @Param("endDate") LocalDate endDate
    	);


    /* =========================================================
       Monthly Trend (Income vs Expense)
       ========================================================= */

    @Query(
    	    value = """
    	        SELECT 
    	            DATE_FORMAT(t.date, '%Y-%m') AS month,
    	            SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END) AS income,
    	            SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END) AS expense
    	        FROM transactions t
    	        JOIN bank_account b ON t.bank_account_id = b.id
    	        WHERE b.user_id = :userId
    	          AND t.date BETWEEN :startDate AND :endDate
    	        GROUP BY month
    	        ORDER BY month
    	    """,
    	    nativeQuery = true
    	)
    	List<MonthlyTrendDTO> findMonthlyIncomeExpenseTrend(
    	    @Param("userId") Long userId,
    	    @Param("startDate") LocalDate startDate,
    	    @Param("endDate") LocalDate endDate
    	);
    
    /* =========================================================
    Transactions for AI Insights (User + Date Range)
    ========================================================= */

	 @Query(
	     value = """
	         SELECT t.*
	         FROM transactions t
	         JOIN bank_account b ON t.bank_account_id = b.id
	         WHERE b.user_id = :userId
	           AND t.date BETWEEN :startDate AND :endDate
	         ORDER BY t.date DESC
	     """,
	     nativeQuery = true
	 )
	 List<Transaction> findTransactionsForAI(
	     @Param("userId") Long userId,
	     @Param("startDate") LocalDate startDate,
	     @Param("endDate") LocalDate endDate
	 );

}
