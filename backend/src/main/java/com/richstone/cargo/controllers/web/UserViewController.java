package com.richstone.cargo.controllers.web;

import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserViewController {
    private final UserServiceImpl userService;
    private final ImageServiceImpl imageService;

    @GetMapping("/formForUpdateUser/{id}")
    public String formForUpdateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        Image image = imageService.getImageToUser(user);

        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("encodedImg", encodedImg);
            log.info("Image found and encoded successfully for user with id: {}", id);
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("encodedImg", encodedImg);
                log.info("Default image encoded successfully for user with id: {}", id);
            } catch (IOException e) {
                log.error("Error occurred while reading default image file: {}", e.getMessage());
                return "error-page";
            }
        }
        model.addAttribute("user", user);
        return "profile-page";
    }

    @PostMapping("/save")
    public String updateUser(@ModelAttribute("user") User user) {
        User existingUser = userService.findById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());

        userService.save(existingUser);
        return "redirect:/";
    }


    @GetMapping("/profile/change-password")
    public String changePassword(HttpServletRequest request, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

        model.addAttribute("user", user);

        return "change-password";
    }
}
