package com.example.minio_storage.service.iml;

import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.mapper.AccountMapper;
import com.example.minio_storage.repository.AccountRepository;
import com.example.minio_storage.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    @Override
    public AccountResponse getAccount(String email) {
        return accountMapper.toAccountResponse(accountRepository.findByEmail(email));
    }

    @Override
    public List<AccountResponse> getAllAccount() {
        return accountRepository.findAll().stream().map(accountMapper::toAccountResponse).toList();
    }
}
