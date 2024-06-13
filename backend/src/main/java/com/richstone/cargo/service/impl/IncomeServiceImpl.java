package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.IncomeRequestDto;
import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.exception.IncomeNotFoundException;
import com.richstone.cargo.model.*;
import com.richstone.cargo.repository.IncomeRepository;
import com.richstone.cargo.service.IncomeService;
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
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository incomeRepository;

    public List<Income> findIncomesByTripIds(List<Long> tripId) {
        log.info("Finding incomes by tripIds");

        List<Income> incomes = incomeRepository.findByTripIdIn(tripId)
                .orElseThrow(() -> {
                    log.error("No incomes found for the specified criteria");
                    return new IncomeNotFoundException("No incomes found for the specified criteria", new IncomeNotFoundException());
                });

        log.debug("Retrieving incomes with tripIds: {}", tripId);
        log.info("Incomes found successfully");
        return incomes;
    }

    public void addIncome(TripDto tripDto, Trip trip) {
        log.info("Adding income: " + tripDto.getIncomeDescription());

        Income income = Income.builder()
                .date(LocalDate.now())
                .amount(tripDto.getIncomeAmount())
                .description(tripDto.getIncomeDescription())
                .trip(trip)
                .build();

        incomeRepository.save(income);
        log.info("New income added successfully");
    }

    public List<Double> calculateIncomeForEachTrip(List<Trip> trips) {
        log.info("Calculating income for each trip");

        List<Double> tripIncomes = trips.stream()
                .map(trip -> trip.getIncomes().stream()
                        .mapToDouble(Income::getAmount)
                        .sum())
                .collect(Collectors.toList());

        log.info("Income calculated for each trip successfully");
        return tripIncomes;
    }

    public List<Income> getIncomesByTripId(Long tripId) {
        log.info("Finding incomes by tripId");

        List<Income> incomes = incomeRepository.findByTripId(tripId)
                .orElseThrow(() -> {
                    log.error("No incomes found for the specified criteria");
                    return new IncomeNotFoundException("No incomes found for the specified criteria", new IncomeNotFoundException());
                });

        log.debug("Retrieving incomes with tripIds: {}", tripId);
        log.info("Incomes found successfully");
        return incomes;
    }

    public void saveIncome(IncomeRequestDto requestDto, Trip trip) {
        log.info("Adding income: " + requestDto.getDescription());

        Income income = Income.builder()
                .date(LocalDate.now())
                .amount(requestDto.getAmount())
                .description(requestDto.getDescription())
                .trip(trip)
                .build();

        incomeRepository.save(income);
        log.info("New income added successfully");
    }

    public void delete(Long id) {
        log.info("Deleting income: " + id);
        Optional<Income> income = incomeRepository.findById(id);
        incomeRepository.delete(income.get());
        log.info("Income deleted successfully");

    }

}
