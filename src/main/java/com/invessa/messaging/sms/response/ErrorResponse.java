package com.invessa.messaging.sms.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String code;
    private String status;
    private String message;
    Map<String, List<String>> data = new HashMap<>();
}
