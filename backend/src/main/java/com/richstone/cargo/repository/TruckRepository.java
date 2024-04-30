package com.richstone.cargo.repository;

import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findTruckByDriverId(Long id);
}
