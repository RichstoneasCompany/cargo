package com.richstone.cargo.service.impl;

import com.richstone.cargo.exception.ExpenseNotFoundException;
import com.richstone.cargo.model.Expense;
import com.richstone.cargo.repository.ExpenseRepository;
import com.richstone.cargo.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Override
    public List<Expense> findExpensesByTripIds( List<Long> tripId) {
        log.info("Finding expenses by tripIds");

        List<Expense> expenses = expenseRepository.findByIdIn(tripId)
                .orElseThrow(() -> {
                    log.error("No expenses found for the specified criteria");
                    return new ExpenseNotFoundException("No expenses found for the specified criteria", new ExpenseNotFoundException());
                });

        log.debug("Retrieving expenses with tripIds: {}", tripId);
        log.info("Expenses found successfully");
        return expenses;
    }
}
