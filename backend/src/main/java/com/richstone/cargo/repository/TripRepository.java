package com.richstone.cargo.repository;

import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.model.types.TripStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t.id FROM Trip t WHERE t.assignedDriver.id = :driverId AND t.arrivalTime >= :startDate AND t.arrivalTime <= :endDate")
    Optional<List<Long>> findTripIdsByDriverIdAndPeriod(@Param("driverId") Long driverId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Optional<Trip> findTripByAssignedDriver(Driver driver);

    List<Trip> findAll();

    Optional<Trip> findById(Long id);
    List<Trip> findAllByTripStatus(TripStatus tripStatus);

    List<Trip> findAllByTripStatusIn(List<TripStatus> tripStatuses);

}
