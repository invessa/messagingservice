package com.invessa.messaging.otp.service;


import com.invessa.messaging.otp.model.Otp;

public interface OtpService {

    public Otp generateOtp(String email, String phoneNumber);
    public boolean verifyOtp(String otpValue, String phoneNumber);

}
