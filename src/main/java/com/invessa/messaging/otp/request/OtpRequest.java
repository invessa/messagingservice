package com.invessa.messaging.otp.request;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class OtpRequest {
    private String phoneNumber;
    private String email;

}
