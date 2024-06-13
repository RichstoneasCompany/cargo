package com.richstone.cargo.service.impl;

import com.richstone.cargo.dto.RouteDto;
import com.richstone.cargo.exception.RouteAlreadyExistsException;
import com.richstone.cargo.exception.RouteNotFoundException;
import com.richstone.cargo.mapper.RouteMapper;
import com.richstone.cargo.model.Location;
import com.richstone.cargo.model.Route;
import com.richstone.cargo.repository.RouteRepository;
import com.richstone.cargo.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        String routeName = routeDto.getName();
        log.info("Saving route: {}", routeName);

        Optional<Route> existingRouteOptional = routeRepository.findRouteByName(routeName);

        if (existingRouteOptional.isPresent()) {
            log.info("Route with name '{}' already exists. Skipping save operation.", routeName);
            throw new RouteAlreadyExistsException("Route with same already exists.");
        }
        Location startLocation = routeDto.getStartLocationId();
        Location endLocation = routeDto.getEndLocationId();
        Optional<Route> routeWithSameLocationsOptional = routeRepository.findRouteByStartLocationIdAndEndLocationId(startLocation, endLocation);

        if (routeWithSameLocationsOptional.isPresent()) {
            log.info("Route with the same start and end locations already exists. Skipping save operation.");
            throw new RouteAlreadyExistsException("Route with same already exists.");
        }

        Route route = RouteMapper.INSTANCE.toRoute(routeDto);
        routeRepository.save(route);
        log.info("Route saved successfully: {}", route.getName());
    }

    public Page<Route> getAllRoutes(int pageNo, int pageSize) {
        log.info("Fetching routes for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Route> routes = routeRepository.findAll(pageable);
        log.info("Fetched {} routes", routes.getNumberOfElements());
        return routes;
    }

}
