package com.richstone.cargo.repository;

import com.richstone.cargo.model.Location;
import com.richstone.cargo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findById(Long id);
}
