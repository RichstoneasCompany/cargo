package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.mapper.DriverMapper;
import com.richstone.cargo.mapper.TruckMapper;
import com.richstone.cargo.model.*;
import com.richstone.cargo.service.impl.DriverServiceImpl;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.TruckServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/inactiveDrivers/{pageNo}")
    public String inactiveDriversList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<User> page = userService.getInactiveDrivers(pageNo, pageSize);
        model.addAttribute("drivers", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "inactive-driver-list";
    }

    @GetMapping("/{pageNo}")
    public String driversList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<User> page = userService.getAllDrivers(pageNo, pageSize);
        model.addAttribute("drivers", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "driver-list";
    }

    @GetMapping("/formForUpdateDriver")
    public String formForUpdateDriver(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        Driver driver = driverService.findByUserId(user.getId());
        Truck truckByDriver = truckService.getTruckByDriver(driver);
        TruckDto truck = TruckMapper.INSTANCE.truckToDto(truckByDriver);
        DriverUpdateDto driverUpdateDto = new DriverUpdateDto();
        driverUpdateDto.setDriver(driver);
        driverUpdateDto.setTruck(truck);
        driverUpdateDto.setUser(user);

        Image image = imageService.getImageToUser(user);
        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("driverImage", encodedImg);
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("driverImage", encodedImg);
            } catch (IOException e) {
                return "error-page";
            }
        }
        driverUpdateDto.setImage(image);
        model.addAttribute("driverUpdateDto", driverUpdateDto);
        return "driver-form";
    }

    @PostMapping("/update")
    public String updateDriver(@ModelAttribute("driverUpdateDto") DriverUpdateDto driverUpdateDto) {
        User existingUser = userService.findById(driverUpdateDto.getUser().getId());
        Driver driver = driverService.findByUserId(existingUser.getId());
        Truck truckByDriver = truckService.getTruckByDriver(driver);

        DriverMapper.INSTANCE.updateUserFromDto(driverUpdateDto, existingUser);
        userService.save(existingUser);
        DriverMapper.INSTANCE.updateDriverFromDto(driverUpdateDto, driver);
        driverService.save(driver);
        DriverMapper.INSTANCE.updateTruckFromDto(driverUpdateDto, truckByDriver);
        truckService.save(truckByDriver);

        return "redirect:/drivers/1";
    }

    @PostMapping("/markAsDeleted")
    public String markDriverAsDeleted(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        driverService.markDriverAsDeleted(user);

        return "redirect:/drivers/1";
    }

    @GetMapping("/formForAddDriver")
    public String formForAddDriver(Model model) {
        DriverFormDto driverForm = new DriverFormDto();
        model.addAttribute("driverForm", driverForm);
        return "new-driver-form";
    }

    @PostMapping("/add")
    public String addDriver(@ModelAttribute("driverForm") DriverFormDto driverForm,  @RequestParam("file") MultipartFile file) throws IOException {
        Driver driver = driverService.addDriver(driverForm.getUser());
        Truck truck = truckService.addTruck(driverForm.getTruck());
        truck.setDriver(driver);
        driver.setTruck(truck);
        driver.setLicenseNumber(driverForm.getLicenseNumber());
        driverService.save(driver);
        User user = userService.findByUsername(driverForm.getUser().getUsername());
        truckService.save(truck);
        imageService.uploadImageToDriver(file, user.getId());
        return "redirect:/drivers/1";
    }

    @GetMapping("/deletedDrivers/{pageNo}")
    public String deletedDriversList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<User> page = userService.getDeletedDrivers(pageNo,pageSize);
        model.addAttribute("drivers", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "deleted-drivers-list";
    }

    @PostMapping("/undelete")
    public String undelete(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        driverService.markDriverAsUndeleted(user);
        return "redirect:/drivers/deletedDrivers/1";
    }

    @GetMapping("/details/{id}")
    public String driverDetails(@PathVariable("id") Long id, Model model) {
        Driver driver = driverService.findByUserId(id);
        Image image = imageService.getImageToUser(driver.getUser());
        Truck truckByDriver = truckService.getTruckByDriver(driver);
        TruckDto truck = TruckMapper.INSTANCE.truckToDto(truckByDriver);

        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("driverDetailsImage", encodedImg);
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

    @PostMapping("/delete")
    public String deleteDriver(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        driverService.delete(user);
        return "redirect:/drivers/1";
    }
}
