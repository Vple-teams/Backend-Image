package com.vple.imageserver.Controller;

import com.vple.imageserver.Domain.ImageDto;
import com.vple.imageserver.Domain.ProfileImageDto;
import com.vple.imageserver.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private static final String URL_PREFIX = "https://storage.googleapis.com/";

    private static final String BUCKET_NAME = "vple-bucket";

    @PostMapping(value = "/profile")
    public ResponseEntity<?> uploadMyProfileImage(ProfileImageDto profileImageDto) {
        try {
            String filePath = imageService.uploadProfileImage(profileImageDto.getEmail(), profileImageDto.getMultipartFile());

            return new ResponseEntity<>(URL_PREFIX + BUCKET_NAME + "/" + filePath, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/image")
    public ResponseEntity<?> uploadImage(ImageDto imageDto) {

        try {
            String filePath = imageService.uploadImage(imageDto.getMultipartFile());

            return new ResponseEntity<>(URL_PREFIX + BUCKET_NAME + "/" + filePath, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
