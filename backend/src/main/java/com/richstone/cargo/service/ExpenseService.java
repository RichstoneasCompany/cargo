package com.richstone.cargo.service;

import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.types.ExpenseType;

import java.util.List;

public interface ExpenseService {
    List<Expense> findFinesByTripIds(List<Long> tripId, ExpenseType expenseType);
}
