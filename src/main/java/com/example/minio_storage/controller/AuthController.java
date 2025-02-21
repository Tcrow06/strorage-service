package com.example.minio_storage.controller;

import com.example.minio_storage.dto.request.ActiveAccountRequest;
import com.example.minio_storage.dto.request.RegisterAccountRequest;
import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.dto.response.ApiResponse;
import com.example.minio_storage.service.AccountService;
import com.example.minio_storage.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    AuthenticationService authenticationService;
    AccountService accountService;

    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<?> register(@Valid RegisterAccountRequest request){
        return ApiResponse.builder()
                .message(authenticationService.register(request))
                .build();
    }
    @PostMapping(value = "/active")
        ApiResponse<?> activeAccount(@RequestBody @Valid ActiveAccountRequest request){
        return ApiResponse.builder()
                .message(authenticationService.activeAccount(request))
                .build();
    }
    @PostMapping(value = "/resend-otp")
    ApiResponse<?> resendOtp(@RequestBody @Valid ActiveAccountRequest request){
        return ApiResponse.builder()
                .message(authenticationService.resendOtp(request))
                .build();
    }


    //Chỉ test không đúng file
    @GetMapping(value = "/{email}")
    ApiResponse<AccountResponse> getAccount(@PathVariable String email){
        return ApiResponse.<AccountResponse>builder()
                .result(accountService.getAccount(email))
                .build();
    }
    @GetMapping()
    ApiResponse<List<AccountResponse>> getAllAccount(){
        return ApiResponse.<List<AccountResponse>>builder()
                .result(accountService.getAllAccount())
                .build();
    }



}
