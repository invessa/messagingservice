package com.invessa.messaging.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String code;
    private String status;
    private String message;
    private Map<String,String> data;
}
