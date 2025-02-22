package com.example.minio_storage.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR), // 500
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),

    INVALID_KEY(1005, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission ", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min} ", HttpStatus.BAD_REQUEST),

    UNABLE_DOWNLOAD_FILE(1009,"Unable to download file", HttpStatus.BAD_REQUEST),
    UNABLE_UPLOAD_FILE(1010,"Unable to upload file", HttpStatus.BAD_REQUEST),
    INVALID_FILE(1011,"File avatar not null",HttpStatus.BAD_REQUEST),
    INVALID_CODE_OTP(1012,"Invalid OTP code", HttpStatus.BAD_REQUEST),
    ERROR_CONFIRMED(1013,"Error confirmed", HttpStatus.BAD_REQUEST),
    EXPIRED_OTP(1014,"Expired OTP code! Please check email", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1005, "Email existed", HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatusCode statusCode;
}
