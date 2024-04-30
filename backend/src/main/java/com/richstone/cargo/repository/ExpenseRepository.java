package com.richstone.cargo.repository;

import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<List<Expense>> findByIdIn(List<Long> tripId);
}
