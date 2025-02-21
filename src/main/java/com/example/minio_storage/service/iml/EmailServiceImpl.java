package com.example.minio_storage.service.iml;

import com.example.minio_storage.service.BaseRedisService;
import com.example.minio_storage.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class EmailServiceImpl implements EmailService {
    JavaMailSender javaMailSender;
    BaseRedisService baseRedisService;
    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message  = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        log.info("Email sent");
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        String redis = getRedisKey(email);
        String storedOtp = (String) baseRedisService.get(redis);
        if (storedOtp!=null && storedOtp.equals(otp)){
            baseRedisService.delete(redis);
            return true;
        }
        return false;
    }

    @Override
    public void saveOtp(String email, String otp) {
        baseRedisService.set(getRedisKey(email), otp);
    }

    @Override
    public String getRedisKey(String email) {
        return "OTP_"+ email;
    }
}
