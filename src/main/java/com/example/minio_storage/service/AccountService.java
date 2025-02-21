package com.example.minio_storage.service;

import com.example.minio_storage.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse getAccount(String email);
    List<AccountResponse> getAllAccount();
}
