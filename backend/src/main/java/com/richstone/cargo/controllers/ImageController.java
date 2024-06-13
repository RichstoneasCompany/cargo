package com.richstone.cargo.controllers;

import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.ImageServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "ImageController", description = "API для работы с изображениями пользователей")
public class ImageController {

    private final ImageServiceImpl imageService;
    private final UserServiceImpl userService;

    @PostMapping
    @Operation(summary = "Загрузка изображения пользователя",
            description = "Позволяет пользователю загрузить изображение для своего профиля")
    public ResponseEntity<String> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                               Principal principal) throws IOException {
        imageService.uploadImageToUser(file, principal);
        return ResponseEntity.ok().body("Image Uploaded Successfully");
    }

    @GetMapping("/profile")
    @Operation(summary = "Получение изображения пользователя",
            description = "Возвращает изображение профиля текущего пользователя")
    public ResponseEntity<Image> getImageForUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Image userImage = imageService.getImageToUser(user);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @PostMapping("/driver/{userId}")
    @Operation(summary = "Загрузка изображения водителя",
            description = "Позволяет загрузить изображение водителя")
    public ResponseEntity<String> uploadImageToDriver(@RequestParam("file") MultipartFile file, @PathVariable("userId") Long userId) throws IOException {
        imageService.uploadImageToDriver(file, userId);
        return ResponseEntity.ok().body("Image Uploaded Successfully");
    }
}
