package com.invessa.messaging.sms.request;

import lombok.Data;

@Data
public class SMSRequest {
    private String phoneNumber;
    private String smsText;
}
