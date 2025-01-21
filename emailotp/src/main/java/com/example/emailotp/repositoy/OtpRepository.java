package com.example.emailotp.repositoy;

import com.example.emailotp.entity.Otp;
import com.example.emailotp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmailAndOtp(String email, String otpId);

    Optional<Otp> findByEmailAndOtpAndType(String email, String otp, String forgotPassword);
}

