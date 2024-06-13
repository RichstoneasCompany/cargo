package com.richstone.cargo.controllers.web;

import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Base64;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final ImageServiceImpl imageService;
    private final UserServiceImpl userService;

    @GetMapping("/")
    public String home(Principal principal,Model model){
        User user = userService.getUserByPrincipal(principal);
        Image image = imageService.getImageToUser(user);

        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("encodedImg", encodedImg);
            log.info("Image found and encoded successfully for user with id: {}", user.getId());
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("encodedImg", encodedImg);
                log.info("Default image encoded successfully for user with id: {}", user.getId());
            } catch (IOException e) {
                log.error("Error occurred while reading default image file: {}", e.getMessage());
                return "error-page";
            }
        }
        return "landing-page";
    }


}