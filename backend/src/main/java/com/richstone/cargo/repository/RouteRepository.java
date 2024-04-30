package com.richstone.cargo.repository;

import com.richstone.cargo.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findById(Long id);

    List<Route> findAll();
}
