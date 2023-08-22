package com.invessa.messaging.sms.providers.vanso.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SMSData {

    private String dest;
    private String src;
    private String text;

}
