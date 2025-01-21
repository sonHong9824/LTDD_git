package com.example.emailotp.controller;

import com.example.emailotp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String email){
        otpService.sendOtp(email);
        return "Otp sent";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp){
        return otpService.verifyOtp(email, otp);
    }
}
