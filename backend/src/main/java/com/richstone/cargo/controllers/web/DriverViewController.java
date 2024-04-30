package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.model.*;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.TruckServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverViewController {
    private final UserServiceImpl userService;
    private final DriverServiceImpl driverService;
    private final ImageServiceImpl imageService;
    private final TruckServiceImpl truckService;

    @GetMapping("/inactiveDrivers")
    public String inactiveDriversList(Model model) {
        List<User> drivers = userService.getInactiveDrivers();
        model.addAttribute("drivers", drivers);
        return "inactive-driver-list";
    }

    @GetMapping
    public String driversList(Model model) {
        List<User> drivers = userService.getAllDrivers();
        model.addAttribute("drivers", drivers);
        return "driver-list";
    }

    @GetMapping("/formForUpdateDriver")
    public String formForUpdateDriver(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        Driver driver = driverService.findByUserId(user.getId());
        TruckDto truck = truckService.getTruckByDriver(driver);
        model.addAttribute("user", user);
        model.addAttribute("truck", truck);
        return "driver-form";
    }

    @PostMapping("/save")
    public String updateDriver(@ModelAttribute("user") User user, @RequestParam(value = "isEnabled", required = false) boolean isEnabled) {
        User existingUser = userService.findById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        existingUser.setEnabled(isEnabled);

        userService.save(existingUser);
        return "redirect:/drivers/inactiveDrivers";
    }

    @PostMapping("/delete")
    public String deleteDriver(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        driverService.delete(user);
        userService.delete(user);

        return "redirect:/drivers";
    }

    @GetMapping("/formForAddDriver")
    public String formForAddDriver(Model model) {
        DriverFormDto driverForm = new DriverFormDto();
        model.addAttribute("driverForm", driverForm);
        return "new-driver-form";
    }

    @PostMapping("/add")
    public String addDriver(@ModelAttribute("driverForm") DriverFormDto driverForm) {
        Driver driver = driverService.addDriver(driverForm.getUser());
        Truck truck = truckService.addTruck(driverForm.getTruck());
        truck.setDriver(driver);
        driver.setTruck(truck);
        driver.setLicenseNumber(driverForm.getLicenseNumber());
        driverService.save(driver);
        truckService.save(truck);
        return "redirect:/drivers";
    }

    @GetMapping("/deletedDrivers")
    public String deletedDriversList(Model model) {
        List<User> drivers = userService.getDeletedDrivers();
        model.addAttribute("drivers", drivers);
        return "deleted-drivers-list";
    }

    @PostMapping("/undelete")
    public String undelete(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        driverService.undelete(user);

        return "redirect:/drivers/deletedDrivers";
    }

    @GetMapping("/details/{id}")
    public String driverDetails(@PathVariable("id") Long id, Model model) {
        Driver driver = driverService.findByUserId(id);
        Image image = imageService.getImageToUser(driver.getUser());
        TruckDto truck = truckService.getTruckByDriver(driver);
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
        model.addAttribute("driver", driver);
        model.addAttribute("image", image);
        model.addAttribute("truck", truck);
        return "driver-details-page";
    }

}
