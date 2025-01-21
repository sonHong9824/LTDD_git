package com.example.emailotp.service;

import com.example.emailotp.entity.User;
import com.example.emailotp.repositoy.OtpRepository;
import com.example.emailotp.entity.Otp;
import com.example.emailotp.repositoy.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    public String sendOtp(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return "Email đã được sử dụng";
        }

        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        Otp otp1 = new Otp();
        otp1.setEmail(email);
        otp1.setOtp(otp);
        otp1.setExpirationTime(expiryTime);
        otp1.setType("REGISTER");
        otpRepository.save(otp1);

        emailService.sendOtpEmail(email, otp);

        return "Đã gửi OTP";

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

    public String sendForgotPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            return "Email không tồn tại";
        }

        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        Otp otp1 = new Otp();
        otp1.setEmail(email);
        otp1.setOtp(otp);
        otp1.setExpirationTime(expiryTime);
        otp1.setType("FORGOT_PASSWORD");
        otpRepository.save(otp1);

        emailService.sendOtpEmail(email, otp);
        return "OTP đặt lại mật khẩu đã được gửi";

    }

    public String verifyForgotPassword(String email, String otp) {
        Optional<Otp> record = otpRepository.findByEmailAndOtpAndType(email, otp, "FORGOT_PASSWORD");
        if(record.isPresent()) {
            Otp otpRecord = record.get();
            if(otpRecord.getExpirationTime().isBefore(LocalDateTime.now())) {
                return "OTP hết hạn";
            }
            otpRecord.setVerified(true);
            otpRepository.save(otpRecord);
            return "OTP xác nhận thành công";
        }
        return "OTP không chính xác";
    }
}
