package com.jmt.mypage.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true) // 없는 필드는 무시하게끔 추가
@Data
public class RequestProfile {
    @NotBlank  // 이름빼고 나머지는 필수가 아님
    private String userName;

    private String password;

    private String confirmPassword;

    private String mobile;
}
