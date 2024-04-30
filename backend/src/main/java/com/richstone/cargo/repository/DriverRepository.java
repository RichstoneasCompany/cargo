package com.richstone.cargo.repository;

import com.richstone.cargo.model.Driver;
import com.richstone.cargo.model.Truck;
import com.richstone.cargo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUserId(Long userId);

    @Query("SELECT d FROM Driver d WHERE d.user = :user")
    Driver findDriverByUser(User user);

    Optional<Driver> findById(Long id);

    List<Driver> findAll();
}
