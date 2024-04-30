package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.TripDto;
import com.richstone.cargo.dto.TruckDto;
import com.richstone.cargo.model.*;
import com.richstone.cargo.repository.DriverRepository;
import com.richstone.cargo.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripViewController {
    private final TripServiceImpl tripService;
    private final UserServiceImpl userService;
    private final RouteServiceImpl routeService;
    private final DriverRepository driverRepository;
    private final ImageServiceImpl imageService;
    private final TruckServiceImpl truckService;

    @PostMapping("/save")
    public String save(@ModelAttribute("trip") TripDto tripDto) {
        tripService.addTrip(tripDto);
        return "redirect:/trips";
    }

    @GetMapping
    public String tripList(Model model) {
        List<Trip> trips = tripService.getAllTrips();
        model.addAttribute("trips", trips);
        return "trip-page";
    }

    @GetMapping("/formForAddTrip")
    public String formForAddTrip(Model model) {
        TripDto trip = new TripDto();
        List<User> drivers = userService.getAllDrivers();
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("drivers", drivers);
        model.addAttribute("trip", trip);
        model.addAttribute("routes", routes);
        return "trip-form";
    }

    @GetMapping("/formForUpdateTrip")
    public String formForUpdateTrip(@RequestParam("id") Long id, Model model) {
        Trip trip = tripService.findById(id);
        List<Driver> drivers = driverRepository.findAll();
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("drivers", drivers);
        model.addAttribute("routes", routes);
        model.addAttribute("trip", trip);
        return "trip-update-form";
    }

    @PostMapping("/update")
    public String updateTrip(@ModelAttribute("trip") Trip trip) {
        Trip existingTrip = tripService.findById(trip.getId());
        existingTrip.setRoute(trip.getRoute());
        existingTrip.setTripStatus(trip.getTripStatus());
        existingTrip.setDepartureTime(trip.getDepartureTime());
        existingTrip.setArrivalTime(trip.getArrivalTime());
        existingTrip.setAssignedDriver(trip.getAssignedDriver());
        tripService.save(existingTrip);
        return "redirect:/trips";
    }

    @GetMapping("/activeTrips")
    public String activeTripsList(Model model) {
        List<Trip> trips = tripService.getActiveTrips();
        model.addAttribute("trips", trips);
        return "active-trips-page";
    }

    @GetMapping("/archive")
    public String tripsHistoryList(Model model) {
        List<Trip> trips = tripService.getTripsHistory();
        model.addAttribute("trips", trips);
        return "active-trips-page";
    }

    @GetMapping("/details/{id}")
    public String tripDetails(@PathVariable("id") Long id, Model model) {
        Trip trip = tripService.findById(id);
        User driver = trip.getAssignedDriver().getUser();
        Image image = imageService.getImageToUser(driver);
        TruckDto truck = truckService.getTruckByDriver(trip.getAssignedDriver());
        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("encodedImg", encodedImg);
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("encodedImg", encodedImg);
            } catch (IOException e) {
                return "error-page";
            }
        }
        model.addAttribute("trip", trip);
        model.addAttribute("driver", driver);
        model.addAttribute("image", image);
        model.addAttribute("truck", truck);
        return "trip-details-page";
    }

    @PostMapping("/delete")
    public String deleteDispatcher(@RequestParam("id") Long id) {
        tripService.cancelTrip(id);
        return "redirect:/activeTrips";
    }
}
