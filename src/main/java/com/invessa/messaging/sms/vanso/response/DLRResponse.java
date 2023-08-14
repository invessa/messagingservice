package com.invessa.messaging.sms.vanso.response;

import lombok.Data;

@Data
public class DLRResponse {
    String createDate;
    String destination;
    int errorCode;
    String errorMessage;
    String finalDate;
    boolean isFinal;
    String status;
    String ticketId;

}
