package com.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finance.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBankAccountId(Long bankAccountId);
    
    //Note: JPQL uses Entity name instead of Database table name
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'EXPENSE' AND MONTH(t.date)=:month AND YEAR(t.date)=:year")
    double getMonthlyExpense(int month, int year);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'INCOME' AND MONTH(t.date)=:month AND YEAR(t.date)=:year")
    double getMonthlyIncome(int month, int year);
    
    @Query("""
    		SELECT t.category, SUM(t.amount)
	       FROM Transaction t 
	       WHERE t.type='EXPENSE' AND MONTH(t.date)=:month AND YEAR(t.date)=:year 
	       GROUP BY t.category""")
	List<Object[]> getCategorySummary(int month, int year);
	
	@Query("""
			SELECT CONCAT(YEAR(t.date), '-', MONTH(t.date)) AS month, SUM(t.amount) 
	       FROM Transaction t 
	       WHERE t.type='EXPENSE'
	       GROUP BY YEAR(t.date), MONTH(t.date)
	       ORDER BY YEAR(t.date), MONTH(t.date)""")
		List<Object[]> getMonthlyTrend();
}
