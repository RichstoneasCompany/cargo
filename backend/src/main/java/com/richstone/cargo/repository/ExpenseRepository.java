package com.richstone.cargo.repository;

import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.types.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<List<Expense>> findByTripIdInAndExpenseType(List<Long> tripId, ExpenseType expenseType);
    Optional<List<Expense>> findByTripId(Long id);
}
