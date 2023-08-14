package com.invessa.messaging.request;

import com.invessa.messaging.NotificationEnumTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.Instant;

@Data
public class NotificationRequest {

    @Enumerated(EnumType.STRING)
    NotificationEnumTypes channel;
    String message;
    Instant created_at;
}
