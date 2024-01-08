package com.mamun.onlinebooklibrary.model;


public class ResponseDto {
    private Integer userId;
    private String email;
    private String AccessToken;

    public ResponseDto() {

    }

    public ResponseDto(Integer userId, String email, String accessToken) {
        this.userId = userId;
        this.email = email;
        AccessToken = accessToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}

