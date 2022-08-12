package com.vple.imageserver;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Storage storage;

    private static final String BUCKET_NAME = "vple-bucket";

    public String uploadProfileImage(String filename, String email, MultipartFile multipartFile) throws IOException {

        final String filePath = email + "/profile/";

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

        return filePath + filename;
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
