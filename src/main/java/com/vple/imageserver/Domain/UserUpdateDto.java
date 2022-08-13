package com.vple.imageserver.Domain;

import lombok.Data;

@Data
public class UserUpdateDto {

    private String url;

    private String email;

    public UserUpdateDto(String url, String email) {
        this.url = url;
        this.email = email;
    }
}
