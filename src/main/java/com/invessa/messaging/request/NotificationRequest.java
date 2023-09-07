package com.invessa.messaging.request;

import com.invessa.messaging.enums.NotificationEnumTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.Instant;

@Data
public class NotificationRequest {

    @Enumerated(EnumType.STRING)
    NotificationEnumTypes channel;
    String sms_message;
    String phone_number;
    String email_address;
    String email_type;
    String first_name;
    String last_name;
}
