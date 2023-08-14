package com.invessa.messaging.sms.vanso.response;

import lombok.Data;

@Data
public class VansoErrorResponse {
    private String errorCode;
    private String errorMessage;
}
