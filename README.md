# Backend-Image
GCP storage에 image를 업로드하는 서버입니다.

### 사용자 이미지 저장 구조
![저장구조](https://user-images.githubusercontent.com/58351498/187669318-81826ec4-ddc9-4195-9f77-57d30c020655.jpg)

### Request & Response
모든 호출 API의 HTTP method는 POST입니다.
#### 1. user profile image
Endpoint: http://localhost:8082/profile
- Request
``` json
{
    "filename": "{프로필 이미지 파일명}",
    "multipartFile": "{업로드할 파일}",
    "email": "{업로드하는 사용자의 이메일}"
}
```

- Response 이미지 url

"https://storage.googleapis.com/vple-bucket/{파일이 저장된 위치}"

#### 2. post image
Endpoint: http://localhost:8082/post

``` json
{
    "multipartFile": "{업로드할 파일}",
    "email": "{업로드하는 사용자의 이메일}"
}
```

- Response 이미지 url

"https://storage.googleapis.com/vple-bucket/{파일이 저장된 위치}"

#### 3. plogging image
Endpoint: http://localhost:8082/plogging
``` json
{
    "multipartFile": "{업로드할 파일}",
    "email": "{업로드하는 사용자의 이메일}"
}
```

- Response

"success"
