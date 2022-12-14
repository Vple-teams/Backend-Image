package com.vple.imageserver.Service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.vple.imageserver.Domain.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Storage storage;

    private static final String BUCKET_NAME = "vple-bucket";

    private static final String URL_PREFIX = "https://storage.googleapis.com/";

    public String uploadProfileImage(String email, MultipartFile multipartFile) throws IOException {
        String randomFilename = UUID.randomUUID().toString();
        final String filePath = "profile/";
        final String URL = "https://vple-backend.all.gagark.shop/api/user/profile";

        Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.currentDirectory(), Storage.BlobListOption.prefix(filePath));
        for(Blob blob : blobs.iterateAll()) {
            storage.delete(blob.getBlobId());
        }

        storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, filePath + randomFilename)
                        .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                multipartFile.getBytes()
        );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, new UserUpdateDto(
                URL_PREFIX + BUCKET_NAME + "/" + filePath + randomFilename, email), String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return filePath + randomFilename;
        }
        else {
            throw new IllegalStateException("?????? ???????????? ????????? ??????????????????.");
        }
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        String randomFilename = UUID.randomUUID().toString();
        final String filePath = "image/";

        storage.create(
                BlobInfo.newBuilder(BUCKET_NAME, filePath + randomFilename)
                        .setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(),
                multipartFile.getBytes()
        );

        return filePath + randomFilename;
    }

}
