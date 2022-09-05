package com.vple.imageserver.Domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {

    private MultipartFile multipartFile;

}
