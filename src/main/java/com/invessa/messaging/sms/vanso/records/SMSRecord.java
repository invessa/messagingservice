package com.invessa.messaging.sms.vanso.records;

public record SMSRecord(String dest, String src, String text, boolean unicode) {
}
