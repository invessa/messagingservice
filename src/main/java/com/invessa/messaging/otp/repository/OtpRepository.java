package com.invessa.messaging.otp.repository;

import com.invessa.messaging.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Long> {

    Optional<Otp> findByOtpValue(String otpValue);
    Optional<Otp> findByOtpValueAndPhoneNumber(String phoneNumber, String otpValue);
}
