package com.richstone.cargo.service;

import com.richstone.cargo.model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> findExpensesByTripIds(List<Long> tripId);
}
