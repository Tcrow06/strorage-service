package com.example.minio_storage.service;

import com.example.minio_storage.dto.request.ActiveAccountRequest;
import com.example.minio_storage.dto.request.RegisterAccountRequest;

public interface AuthenticationService {
    String register(RegisterAccountRequest request);
    String activeAccount(ActiveAccountRequest request);
    String resendOtp(ActiveAccountRequest request);

}
