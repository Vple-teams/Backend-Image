package com.vple.imageserver;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostImageDto {

    private MultipartFile multipartFile;

    private String filename;

    private String email;

    private String postId;
}
