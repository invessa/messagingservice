package com.invessa.messaging.sms.vanso.records;

public record DLRRecord(String createDate,String destination, int errorCode,
                        String errorMessage, String finalDate, boolean isFinal,
                        String status, String ticketId) {
}
