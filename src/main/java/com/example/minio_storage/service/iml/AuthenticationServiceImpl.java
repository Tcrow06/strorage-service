package com.example.minio_storage.service.iml;

import com.example.minio_storage.common.utils.OtpGenerator;
import com.example.minio_storage.dto.request.ActiveAccountRequest;
import com.example.minio_storage.dto.request.RegisterAccountRequest;
import com.example.minio_storage.dto.response.AccountResponse;
import com.example.minio_storage.entity.AccountEntity;
import com.example.minio_storage.exception.AppException;
import com.example.minio_storage.exception.ErrorCode;
import com.example.minio_storage.integration.MinioChannel;
import com.example.minio_storage.mapper.AccountMapper;
import com.example.minio_storage.repository.AccountRepository;
import com.example.minio_storage.service.AuthenticationService;
import com.example.minio_storage.service.BaseRedisService;
import com.example.minio_storage.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    MinioChannel minioChannel;
    EmailService emailService;
    BaseRedisService baseRedisService;

    @Override
    public String register(RegisterAccountRequest request){
        AccountEntity account = accountRepository.findByEmail(request.getEmail());
        if(account!=null){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        account = accountRepository.findByUsername(request.getUsername());
        if(account!=null){
            throw  new AppException(ErrorCode.USER_EXISTED);
        }

        account = new AccountEntity();
        account.setEmail(request.getEmail());
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        if(request.getAvatar()!= null){
            account.setAvatar(minioChannel.upload(request.getAvatar()));
        }
        accountRepository.save(account);

        String otpCode = OtpGenerator.generateOtp();
        emailService.saveOtp(request.getEmail(),otpCode);
        String subject = "🔑 Activate Your Account at Tcrow!";
        String body = "Hello " + request.getUsername() + ",\n\n"
                + "Thank you for signing up at Tcrow.com. To activate your account, please use the following OTP code:\n\n"
                + "🔒 Your OTP Code: " + otpCode + "\n\n"
                + "This code is valid for the next 10 minutes. Please do not share this code with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The Trcow Team";
        emailService.sendEmail(request.getEmail(),subject,body);

        return "Account created successfully. Please check your email to activate your account.";
    }

    @Override
    public String activeAccount(ActiveAccountRequest request) {
        String getOpt = (String) baseRedisService.get(emailService.getRedisKey(request.getEmail()));
        if(getOpt==null || ! getOpt.equals(request.getOtpCode())){
            throw new AppException(ErrorCode.INVALID_CODE_OTP);
        }
        AccountEntity acc = accountRepository.findByEmail(request.getEmail());
        if(acc.getIsActive().equals(1)){
            throw new AppException(ErrorCode.ERROR_CONFIRMED);
        }
        acc.setIsActive(1);
        accountRepository.save(acc);
        return "Account active successfully.";
    }

    @Override
    public String resendOtp(ActiveAccountRequest request) {
        AccountEntity account = accountRepository.findByEmail(request.getEmail());
        if(account==null){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if(account.getIsActive().equals(1)){
            throw new AppException(ErrorCode.ERROR_CONFIRMED);
        }
        baseRedisService.delete(emailService.getRedisKey(request.getEmail()));
        String newOtp = OtpGenerator.generateOtp();
        baseRedisService.set(emailService.getRedisKey(request.getEmail()),newOtp );
        String subject = "🔑 Resend OTP for Your Tcrow Account";
        String body = "Hello " + account.getUsername() + ",\n\n"
                + "We noticed that you requested a new OTP to activate your account. Please find your new OTP code below:\n\n"
                + "🔒 Your OTP Code: " + newOtp + "\n\n"
                + "This code is valid for the next 10 minutes. Please do not share this code with anyone.\n\n"
                + "If you did not request this, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The Tcrow Team";
        emailService.sendEmail(request.getEmail(),subject,body);
        return "Opt code successfully resent please check email";
    }

}