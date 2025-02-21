package com.example.minio_storage.service;

import com.example.minio_storage.dto.request.RegisterAccountRequest;
import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.entity.AccountEntity;
import com.example.minio_storage.exception.AppException;
import com.example.minio_storage.exception.ErrorCode;
import com.example.minio_storage.integration.MinioChannel;
import com.example.minio_storage.mapper.AccountMapper;
import com.example.minio_storage.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    PasswordEncoder passwordEncoder;
    MinioChannel minioChannel;
    public AccountResponse register(RegisterAccountRequest request){
        if (request.getAvatar() == null) {
            throw new AppException(ErrorCode.INVALID_FILE);
        }
        final var account = new AccountEntity();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setAvatar(minioChannel.upload(request.getAvatar()));
        AccountEntity account1 = accountRepository.save(account);
        return accountMapper.toAccountResponse(account1);
    }
}
