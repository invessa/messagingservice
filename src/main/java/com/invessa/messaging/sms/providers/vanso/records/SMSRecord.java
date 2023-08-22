package com.invessa.messaging.sms.providers.vanso.records;

public record SMSRecord(String dest, String src, String text, boolean unicode) {
}
