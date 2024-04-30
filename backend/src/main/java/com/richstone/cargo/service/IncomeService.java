package com.richstone.cargo.service;

import com.richstone.cargo.model.Income;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeService {
   List<Income> findIncomesByTripIds(List<Long> tripId);
}
