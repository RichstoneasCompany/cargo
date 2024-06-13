package com.richstone.cargo.controllers.web;

import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Base64;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalViewController {

    private final UserServiceImpl userService;
    private final ImageServiceImpl imageService;



    @ModelAttribute("encodedImg")
    public void addUserImageToModel(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.getUserByPrincipal(principal);
            Image image = imageService.getImageToUser(user);

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
                    model.addAttribute("encodedImg", "default image or error image base64 string");
                }
            }
        }
    }
}
