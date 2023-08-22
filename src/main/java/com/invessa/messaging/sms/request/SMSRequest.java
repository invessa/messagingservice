package com.invessa.messaging.sms.request;

import lombok.Data;

@Data
public class SMSRequest {
    private String phone_number;
    private String sms_text;
}
