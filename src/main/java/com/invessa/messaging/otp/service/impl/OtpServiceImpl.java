package com.invessa.messaging.otp.service.impl;

import com.invessa.messaging.otp.model.Otp;
import com.invessa.messaging.otp.repository.OtpRepository;
import com.invessa.messaging.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    @Value("${invessa.otp.lifespan}")
    private int otpLifespan;

    @Autowired
    private OtpRepository otpRepository;

    public Otp generateOtp(String email,String phoneNumber) {
        Otp otp = new Otp();
        otp.setOtpValue(String.format("%06d", new Random().nextInt(1000000)));
        otp.setPhoneNumber(phoneNumber);
        otp.setEmail(email);
        otp.setExpirationDateTime(LocalDateTime.now().plusSeconds(otpLifespan));

        // Send SMS


        // Send Email

        return otpRepository.save(otp);
    }

    public boolean verifyOtp(String otpValue, String phoneNumber) {
        Optional<Otp> otpOptional = otpRepository.findByOtpValueAndPhoneNumber(otpValue, phoneNumber);

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();

            if (otp.getExpirationDateTime().isAfter(LocalDateTime.now())) {
                otpRepository.delete(otp);
                return true;
            }
        }

        return false;
    }
}
