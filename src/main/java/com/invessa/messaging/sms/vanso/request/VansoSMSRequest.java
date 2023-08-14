package com.invessa.messaging.sms.vanso.request;

import lombok.Data;

@Data
public class VansoSMSRequest {
    String dest;
    String src;
    String text;
    boolean unicode;
}
