package com.example.minio_storage.mapper;

import com.example.minio_storage.dto.request.RegisterAccountRequest;
import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toAccountResponse(AccountEntity account);
}
