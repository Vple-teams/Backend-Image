# Backend-Image
GCP storage에 image를 업로드하는 서버입니다.

### 사용자 이미지 저장 구조
![저장구조](https://user-images.githubusercontent.com/58351498/187669318-81826ec4-ddc9-4195-9f77-57d30c020655.jpg)
위 다이어그램은 수정 예정
### Request & Response
모든 호출 API의 HTTP method는 POST입니다.
#### 1. user profile image
Endpoint: http://{SERVER_URL}:8082/profile
- Request
``` json
{
    "multipartFile": "{업로드할 파일}",
    "email": "{업로드하는 사용자의 이메일}"
}
```
데이터베이스에도 사용자 대표 이미지의 url을 저장해야 하므로 email 주소가 필요합니다. 
- Response 이미지 url

`"https://storage.googleapis.com/vple-bucket/{파일의 UUID + 확장자}"`

#### 2. image - 프로필 이미지 외의 모든 이미지 업로드 링크
Endpoint: http://{SERVER_URL}:8082/image

``` json
{
    "multipartFile": "{업로드할 파일}"
}
```

- Response 이미지 url

`"https://storage.googleapis.com/vple-bucket/{파일의 UUID + 확장자}"`

