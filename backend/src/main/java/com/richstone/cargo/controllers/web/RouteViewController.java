package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.RouteDto;
import com.richstone.cargo.model.Location;
import com.richstone.cargo.model.Route;
import com.richstone.cargo.service.impl.LocationServiceImpl;
import com.richstone.cargo.service.impl.RouteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/routes")
public class RouteViewController {
    private final RouteServiceImpl routeService;
    private final LocationServiceImpl locationService;

    @GetMapping("/{pageNo}")
    public String routesList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<Route> page = routeService.getAllRoutes(pageNo,pageSize);
        model.addAttribute("routes", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "routes-list";
    }
    @PostMapping("/delete")
    public String deleteLocation(@RequestParam("routeId") Long id) {
        Route route = routeService.findById(id);
        routeService.delete(route);
        return "redirect:/routes/1";
    }

    @GetMapping("/add")
    public String formForAddRoute(Model model) {
       RouteDto route = new RouteDto();
        List<Location> locations = locationService.getAllLocation();
        model.addAttribute("route", route);
        model.addAttribute("locations", locations);
        return "routes-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("route") RouteDto routeDto) {
        routeService.save(routeDto);
        return "redirect:/routes/1";
    }
}
