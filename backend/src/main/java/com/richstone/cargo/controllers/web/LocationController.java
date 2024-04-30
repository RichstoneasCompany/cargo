package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.LocationDto;
import com.richstone.cargo.model.Location;
import com.richstone.cargo.service.impl.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {
    private final LocationServiceImpl locationService;

    @GetMapping
    public String locationList(Model model) {
        List<Location> locations = locationService.getAllLocation();
        model.addAttribute("locations", locations);
        return "location-list";
    }

    @GetMapping("/add")
    public String formForAddLocation(Model model) {
        LocationDto locationDto= new LocationDto();
        model.addAttribute("locationDto", locationDto);
        return "location-form";
    }

    @PostMapping("/save")
    public String saveLocation(@ModelAttribute LocationDto locationDto) {
        locationService.saveLocation(locationDto);
        return "redirect:/locations";
    }

    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("locationId") Long id) {
        Location location = locationService.findById(id);
        locationService.delete(location);
        return "redirect:/locations";
    }

}