package com.richstone.cargo.repository;

import com.richstone.cargo.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    Optional<List<Income>> findByIdIn(List<Long> tripId);
}
