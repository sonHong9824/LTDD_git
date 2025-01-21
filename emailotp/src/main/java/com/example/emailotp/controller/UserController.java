package com.example.emailotp.controller;

import com.example.emailotp.entity.User;
import com.example.emailotp.service.OtpService;
import com.example.emailotp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String email) {

        return otpService.sendOtp(email);
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String otp) {
        return userService.registerUser(email, password, otp);
    }

    @PostMapping("/send-otp-forgot-password")
    public String sendOtpForgotPassword(@RequestParam String email) {
        return otpService.sendForgotPassword(email);
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String otp) {
        return userService.forgetPassword(email, password, otp);
    }
}
