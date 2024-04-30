package com.richstone.cargo.controllers.web;

import com.richstone.cargo.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageViewController {
    private final ImageServiceImpl imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                    Principal principal) throws IOException {
        imageService.uploadImageToUser(file, principal);
        return ResponseEntity.ok().body("Image Uploaded Successfully");
    }


}
