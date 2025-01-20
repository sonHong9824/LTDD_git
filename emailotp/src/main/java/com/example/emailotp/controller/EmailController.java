package com.example.emailotp.controller;

import com.example.emailotp.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/otp")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String email) {
        String otp = generateOtp();
        emailService.sendOtpEmail(email, otp);
        return "OTP has been sent to your email.";
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Random 6-digit number
        return String.valueOf(otp);
    }
}
