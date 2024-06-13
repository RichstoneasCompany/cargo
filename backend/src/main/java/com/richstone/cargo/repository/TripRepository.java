package com.richstone.cargo.repository;

import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.model.types.TripStatus;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t.id FROM Trip t WHERE t.assignedDriver.id = :driverId AND t.arrivalTime >= :startDate AND t.arrivalTime <= :endDate AND t.tripStatus = 'FINISHED'")
    Optional<List<Long>> findTripIdsByDriverIdAndPeriod(@Param("driverId") Long driverId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Optional<Trip> findTripByAssignedDriver(Driver driver);

    List<Trip> findAll();

    Optional<Trip> findById(Long id);

    List<Trip> findAllByTripStatus(TripStatus tripStatus);

    List<Trip> findAllByTripStatusIn(List<TripStatus> tripStatuses);

    Optional<Trip> findByAssignedDriverAndTripStatus(Driver assignedDriver, TripStatus tripStatus);

    Optional<List<Trip>> findAllByAssignedDriverOrderByDepartureTimeAsc(Driver driver);

    Optional<Trip> findByTripNumber(String tripNumber);

    Page<Trip> findAll(Pageable pageable);
    Page<Trip> findAllByTripStatus(TripStatus tripStatus,Pageable pageable);
    Page<Trip> findAllByTripStatusIn(List<TripStatus> tripStatuses, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Trip t WHERE t.assignedDriver.id = :driverId")
    void deleteAllByAssignedDriver(@Param("driverId") Long driverId);

    @Query("SELECT t FROM Trip t WHERE t.assignedDriver.id = :driverId AND t.arrivalTime >= :startDate AND t.arrivalTime <= :endDate AND t.tripStatus = 'FINISHED'")
    Optional<List<Trip>> findTripsByDriverIdAndPeriod(@Param("driverId") Long driverId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Trip> findAllByAssignedDriver(Driver driver);

    @Query("SELECT t.id FROM Trip t WHERE t.assignedDriver.id = :driverId")
    List<Long> findTripIdsByDriverId(@Param("driverId") Long driverId);
}
