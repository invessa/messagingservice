package com.invessa.messaging.sms.providers.vanso.records;

public record SMSStatusRecord(String destination, int errorCode, String errorMessage,
                              String status, String ticketId) {
}
