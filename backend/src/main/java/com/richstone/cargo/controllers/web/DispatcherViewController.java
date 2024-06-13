package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.Trip;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.AdminServiceImpl;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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
@RequestMapping("/dispatchers")
public class DispatcherViewController {
    private final AdminServiceImpl adminService;
    private final UserServiceImpl userService;
    private final ImageServiceImpl imageService;

    @GetMapping("/{pageNo}")
    public String dispatchersList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<User> page = userService.getAllDispatchers(pageNo,pageSize);
        model.addAttribute("dispatchers", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "dispatcher-list";
    }

    @GetMapping("/formForAddDispatcher")
    @Secured("ADMIN")
    public String formForAddDispatcher(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "dispatcher-form";
    }

    @PostMapping("/save")
    public String addDispatcher(@ModelAttribute("user") UserDto user) {
        adminService.addDispatcher(user);
        return "redirect:/dispatchers/1";
    }

    @PostMapping("/delete")
    public String deleteDispatcher(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return "redirect:/dispatchers/1";
    }

    @GetMapping("/formForUpdateDispatcher")
    public String formForUpdateDispatcher(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update-dispatcher-form";
    }

    @PostMapping("/update")
    public String updateDispatcher(@ModelAttribute("user") User user) {
        User existingUser = userService.findById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        userService.save(existingUser);
        return "redirect:/dispatchers/1";
    }

    @GetMapping("/details/{id}")
    public String driverDetails(@PathVariable("id") Long id, Model model) {
        User dispatcher = userService.findById(id);
        Image image = imageService.getImageToUser(dispatcher);

        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("dispatcherImg", encodedImg);
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("dispatcherImg", encodedImg);
            } catch (IOException e) {
                return "error-page";
            }
        }
        model.addAttribute("dispatcher", dispatcher);
        model.addAttribute("image", image);

        return "dispatcher-details-page";
    }

}
