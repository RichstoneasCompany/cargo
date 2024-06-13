package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.ExpenseRequestDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.exception.ExpenseNotFoundException;
import com.richstone.cargo.model.Expense;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.model.types.ExpenseType;
import com.richstone.cargo.repository.ExpenseRepository;
import com.richstone.cargo.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Override
    public List<Expense> findFinesByTripIds(List<Long> tripId, ExpenseType expenseType) {
        log.info("Finding fines by tripIds");

        List<Expense> expenses = expenseRepository.findByTripIdInAndExpenseType(tripId, expenseType)
                .orElseThrow(() -> {
                    log.error("No fine found for the specified criteria");
                    return new ExpenseNotFoundException("No fines found for the specified criteria", new ExpenseNotFoundException());
                });

        log.debug("Retrieving fines with tripIds: {}", tripId);
        log.info("Fines found successfully");
        return expenses;
    }

    public void addExpense(TripDto tripDto, Trip trip) {
        log.info("Adding expense: ");

        double driverSalary = calculateDriverSalary(tripDto.getIncomeAmount());
        Expense expense = Expense.builder()
                .date(LocalDate.now())
                .amount(driverSalary)
                .description("Driver's salary for this trip")
                .trip(trip)
                .expenseType(ExpenseType.DRIVER_SALARY)
                .build();

        expenseRepository.save(expense);
        log.info("New expense added successfully");
    }

    public List<Double> calculateExpenseForEachTrip(List<Trip> trips) {
        log.info("Calculating expenses for each trip");

        List<Double> tripExpenses = trips.stream()
                .map(trip -> trip.getExpenses().stream()
                        .mapToDouble(Expense::getAmount)
                        .sum())
                .collect(Collectors.toList());

        log.info("Expenses calculated for each trip successfully");
        return tripExpenses;
    }


    public List<Expense> getExpensesByTripId(Long tripId) {
        log.info("Finding expenses by tripId");

        List<Expense> expenses = expenseRepository.findByTripId(tripId)
                .orElseThrow(() -> {
                    log.error("No expenses found for the specified criteria");
                    return new ExpenseNotFoundException("No expenses found for the specified criteria", new ExpenseNotFoundException());
                });

        log.debug("Retrieving expenses with tripIds: {}", tripId);
        log.info("Expenses found successfully");
        return expenses;
    }

    public void saveExpense(ExpenseRequestDto requestDto, Trip trip) {
        log.info("Adding expense: " + requestDto.getDescription());

        Expense expense = Expense.builder()
                .date(LocalDate.now())
                .amount(requestDto.getAmount())
                .description(requestDto.getDescription())
                .trip(trip)
                .expenseType(requestDto.getExpenseType())
                .build();

        expenseRepository.save(expense);
        log.info("New expense added successfully");
    }

    public void delete(Long id) {
        log.info("Deleting expense: " + id);
        Optional<Expense> expense = expenseRepository.findById(id);
        expenseRepository.delete(expense.get());
        log.info("Expense deleted successfully");

    }

    private double calculateDriverSalary(Double income) {
        double driverSalary =  income * 0.3;
        log.info("Calculated driver salary: {}", driverSalary);
        return driverSalary;
    }
}
