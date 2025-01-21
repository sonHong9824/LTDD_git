package com.example.emailotp.service;

import com.example.emailotp.entity.User;
import com.example.emailotp.repositoy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    public String registerUser(String email, String password, String otp) {
        String message = otpService.verifyOtp(email, otp);

        if (!message.equals("OTP xác nhận thành công")) {
            return message;
        }

        if(userRepository.findByEmail(email).isPresent()) {
            return "Email đã tồn tại";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);
        return "Đăng ký thành công";

    }
    public String forgetPassword(String email, String password, String otp) {
        String message = otpService.verifyForgotPassword(email, otp);
        if (!message.equals("OTP xác nhận thành công")) {
            return message;
        }
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.get();
        user.setPassword(password);
        userRepository.save(user);
        return "Đã thay đổi mật khẩu";
    }
}
