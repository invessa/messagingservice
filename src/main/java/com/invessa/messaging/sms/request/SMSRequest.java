package com.invessa.messaging.sms.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SMSRequest {
    private String phone_number;
    private String sms_text;
}
