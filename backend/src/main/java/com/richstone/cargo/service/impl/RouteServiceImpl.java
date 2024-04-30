package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.RouteDto;
import com.richstone.cargo.exception.RouteNotFoundException;
import com.richstone.cargo.mapper.RouteMapper;
import com.richstone.cargo.model.Location;
import com.richstone.cargo.model.Route;
import com.richstone.cargo.repository.RouteRepository;
import com.richstone.cargo.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    public Route findById(Long id) {
        log.info("Finding route by id: {}", id);

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Route not found with id: {}", id);
                    return new RouteNotFoundException("Route not found with id " + id);
                });

        log.info("Found route with id: {}", id);
        return route;

    }

    public List<Route> getAllRoutes() {
        log.info("Fetching all routes from the database");
        List<Route> routes = routeRepository.findAll();
        log.info("Retrieved {} routes from the database", routes.size());
        return routes;
    }

    public void delete(Route route) {
        log.info("Deleting route: {}", route.getName());
        routeRepository.delete(route);
        log.info("Route deleted successfully: {}", route.getName());
    }

    public void save(RouteDto routeDto) {
        log.info("Deleting route: {}", routeDto.getName());
        Route route = RouteMapper.INSTANCE.toRoute(routeDto);
        routeRepository.save(route);
        log.info("Route deleted successfully: {}", route.getName());
    }
}
