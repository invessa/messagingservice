package com.invessa.messaging.email.request;

import com.invessa.messaging.email.enums.EmailTypesEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailRequest {
    EmailTypesEnum email_type;
    String to_address;
    String first_name;
    String last_name;
    String otp;
}
