package com.invessa.messaging.sms.providers.vanso.response;

import lombok.Data;

@Data
public class SMSResponse {
    String destination;
    int errorCode;
    String errorMessage;
    String status;
    String ticketId;
}