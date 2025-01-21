package com.example.emailotp.service;

import com.example.emailotp.repositoy.OtpRepository;
import com.example.emailotp.entity.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    public void sendOtp(String email) {
        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        Otp otp1 = new Otp();
        otp1.setEmail(email);
        otp1.setOtp(otp);
        otp1.setExpirationTime(expiryTime);
        otpRepository.save(otp1);

        emailService.sendOtpEmail(email, otp);

    }

    public String verifyOtp(String email, String otp) {
        Optional<Otp> record = otpRepository.findByEmailAndOtp(email, otp);

        if (record.isPresent()) {
            Otp otpRecord = record.get();

            if(otpRecord.getExpirationTime().isBefore(LocalDateTime.now())) {
                return "OTP hết hạn";
            }
            if(otpRecord.isVerified()) {
                return "OTP đã xác nhận trước đó";
            }
            otpRecord.setVerified(true);
            otpRepository.save(otpRecord);
            return "OTP xác nhận thành công";
        } else {
            return "OTP không hợp lệ";
        }
    }

    private String generateOtp() {
        return String.valueOf(100000 + (int)(Math.random() * 900000));
    }
}
