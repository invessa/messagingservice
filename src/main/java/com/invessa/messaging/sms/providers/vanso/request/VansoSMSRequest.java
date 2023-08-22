package com.invessa.messaging.sms.providers.vanso.request;

import com.invessa.messaging.sms.providers.vanso.dto.SMSData;
import lombok.Data;

@Data
public class VansoSMSRequest {
   SMSData sms;
}
