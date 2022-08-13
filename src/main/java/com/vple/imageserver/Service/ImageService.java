package com.vple.imageserver.Service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.vple.imageserver.Domain.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Storage storage;

    private static final String BUCKET_NAME = "vple-bucket";

    private static final String URL_PREFIX = "https://storage.googleapis.com/";

    public String uploadProfileImage(String filename, String email, MultipartFile multipartFile) throws IOException {

        final String filePath = email + "/profile/";
        final String URL = "http://localhost:8080/api/user/profile";

        Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.currentDirectory(), Storage.BlobListOption.prefix(filePath));
        for(Blob blob : blobs.iterateAll()) {
            storage.delete(blob.getBlobId());
        }

        storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, filePath + filename)
                        .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                multipartFile.getBytes()
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, new UserUpdateDto(
                URL_PREFIX + BUCKET_NAME + "/" + filePath + filename, email), String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return filePath + filename;
        }
        else {
            throw new IllegalStateException("파일 업로드에 오류가 발생했습니다.");
        }
    }

    public String uploadPostImage(MultipartFile multipartFile, String filename, String email, String postId) throws IOException {

        final String filePath = email + "/post/" + postId + "/";

        storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, filePath + filename)
                        .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                multipartFile.getBytes()
        );

        return filePath + filename;
    }
}
