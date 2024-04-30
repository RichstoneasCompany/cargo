package com.richstone.cargo.service.impl;

import com.richstone.cargo.model.Image;
import com.richstone.cargo.model.User;
import com.richstone.cargo.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.zip.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl {

    private final ImageRepository imageRepository;
    private final UserServiceImpl userService;

    public static byte[] compressBytes(byte[] imageData, String format) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(format).next();

        imageWriter.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));

        ImageWriteParam jpgWriteParam = imageWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.5f);

        imageWriter.write(null, new IIOImage(ImageIO.read(new ByteArrayInputStream(imageData)), null, null), jpgWriteParam);

        imageWriter.dispose();

        return byteArrayOutputStream.toByteArray();
    }
    private String getImageFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            return switch (contentType) {
                case "image/jpeg" -> "jpeg";
                case "image/png" -> "png";
                case "image/jpg" -> "jpg";
                default -> throw new IllegalArgumentException("Unsupported image format: " + contentType);
            };
        } else {
            throw new IllegalArgumentException("Cannot determine image format from file");
        }
    }

    public void uploadImageToUser(MultipartFile file, Principal principal) throws IOException {
        User user = userService.getUserByPrincipal(principal);
        log.info("Uploading image profile to User {}", user.getUsername());

        Image image = new Image();
        image.setUserId(user.getId());
        image.setImageBytes(compressBytes(file.getBytes(),getImageFormat(file)));
        image.setName(file.getOriginalFilename());

        imageRepository.findByUserId(user.getId()).ifPresent(imageRepository::delete);
        log.info("Saving new profile image for User: {}", user.getUsername());
        imageRepository.save(image);
    }

    public Image getImageToUser(User user) {
        log.info("Retrieving image for user: {}", user.getUsername());

        Image image = imageRepository.findByUserId(user.getId()).orElse(null);
        if (image != null) {
            log.info("Image found for user: {}", user.getUsername());
            image.setImageBytes(image.getImageBytes());
        } else {
            log.warn("No image found for user: {}", user.getUsername());
        }

        return image;
    }

}
