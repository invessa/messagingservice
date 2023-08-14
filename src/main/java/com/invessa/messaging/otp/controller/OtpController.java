package com.invessa.messaging.otp.controller;

import com.invessa.messaging.otp.model.Otp;
import com.invessa.messaging.otp.service.OtpService;
import com.invessa.messaging.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
public class OtpController {



    private final OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateOtp(@RequestParam("phone_number") String phoneNumber, @RequestParam("email")String email) {
        Otp otp = otpService.generateOtp(email,phoneNumber);
        Map<String,String> dataMap = new HashMap<>();
        if(otp.getOtpValue().isEmpty()){
            return new ResponseEntity<>(new MessageResponse("80","failed","Could not generate OTP",dataMap),HttpStatus.EXPECTATION_FAILED);
        }else {
            Map<String, List<String>> result = new HashMap<>();
            dataMap.put("otp", otp.getOtpValue());
            return new ResponseEntity<>(new MessageResponse("00","success","OTP Generated",dataMap),HttpStatus.OK);
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<?> postVerifyOtp(@RequestParam("otp_value") String otpValue, @RequestParam("phone_number") String phoneNumber) {
        Map<String,String> dataMap = new HashMap<>();
        if (otpService.verifyOtp(otpValue, phoneNumber)) {
            return new ResponseEntity<>(new MessageResponse("00","success","OTP verified successfully",dataMap),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("80","failed","Invalid OTP or expired.",dataMap),HttpStatus.BAD_REQUEST);
        }
    }
}
