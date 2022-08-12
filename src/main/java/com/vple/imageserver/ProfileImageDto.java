package com.vple.imageserver;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageDto {

    private String filename;

    private MultipartFile multipartFile;

    private String email;
}
