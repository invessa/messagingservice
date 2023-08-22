package com.invessa.messaging.sms.providers.vanso.request;

import lombok.Data;

import java.util.List;

@Data
public class BulkSMSRequest {
    List<String> destinations;
    String src;
    String text;
}
