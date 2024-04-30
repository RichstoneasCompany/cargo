package com.richstone.cargo.service;

import com.richstone.cargo.model.Route;

import java.util.List;

public interface RouteService {
    Route findById(Long id);
    List<Route> getAllRoutes();
}
