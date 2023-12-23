package com.invessa.messaging.request;

import com.invessa.messaging.email.enums.EmailTypesEnum;
import com.invessa.messaging.enums.NotificationEnumTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.Instant;

@Data
public class NotificationRequest {

    @Enumerated(EnumType.STRING)
    NotificationEnumTypes channel;
    @Enumerated(EnumType.STRING)
    EmailTypesEnum email_type;
    String sms_message;
    String phone_number;
    String email_address;
    String first_name;
    String last_name;
}
