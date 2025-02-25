package com.example.minio_storage.entity;

import com.example.minio_storage.constant.EnumAccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;

    String password;

    @Column(columnDefinition = "TEXT")
    String avatar;

    String email;

    @Enumerated(EnumType.STRING)
    EnumAccountStatus status= EnumAccountStatus.UNVERIFIED;
}
