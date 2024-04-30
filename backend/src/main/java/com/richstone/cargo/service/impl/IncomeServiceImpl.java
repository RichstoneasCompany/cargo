package com.richstone.cargo.service.impl;

import com.richstone.cargo.exception.ExpenseNotFoundException;
import com.richstone.cargo.exception.IncomeNotFoundException;
import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.Income;
import com.richstone.cargo.repository.IncomeRepository;
import com.richstone.cargo.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    public List<Income> findIncomesByTripIds(List<Long> tripId) {
        log.info("Finding incomes by tripIds");

        List<Income> incomes = incomeRepository.findByIdIn(tripId)
                .orElseThrow(() -> {
                    log.error("No incomes found for the specified criteria");
                    return new IncomeNotFoundException("No incomes found for the specified criteria", new IncomeNotFoundException());
                });

        log.debug("Retrieving incomes with tripIds: {}", tripId);
        log.info("Incomes found successfully");
        return incomes;
    }
}
