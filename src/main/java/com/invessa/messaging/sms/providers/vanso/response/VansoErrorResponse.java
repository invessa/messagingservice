package com.invessa.messaging.sms.providers.vanso.response;

import lombok.Data;

@Data
public class VansoErrorResponse {
    private String errorCode;
    private String errorMessage;
}
