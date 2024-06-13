package com.richstone.cargo.repository;

import com.richstone.cargo.model.Topic;
import com.richstone.cargo.model.TripChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripChangeHistoryRepository extends JpaRepository<TripChangeHistory, Long> {
    List<TripChangeHistory> findByTripIdInOrderByChangeTimeDesc(List<Long> tripIds);
}
