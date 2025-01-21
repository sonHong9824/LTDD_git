package com.example.emailotp.controller;

import com.example.emailotp.service.OtpService;
import com.example.emailotp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {
        otpService.sendOtp(email);
        return "Đã gửi OTP";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String otp) {
        return userService.registerUser(email, password, otp);
    }
}
