package com.example.minio_storage.repository;

import com.example.minio_storage.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    AccountEntity findByEmail(String email);
    AccountEntity findByUsername(String username);
}
