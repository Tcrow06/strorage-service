package com.example.minio_storage.controller;

import com.example.minio_storage.dto.request.RegisterAccountRequest;
import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.dto.response.ApiResponse;
import com.example.minio_storage.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    AuthenticationService authenticationService;

    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<AccountResponse> register(@Valid RegisterAccountRequest request){
        return ApiResponse.<AccountResponse>builder()
                .result(authenticationService.register(request))
                .build();
    }

}
