package com.invessa.messaging.sms.controller;

import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class SMSController {

    @Autowired
    SMSService smsService;

    @PostMapping("/sms")
    public ResponseEntity<?>postSendSMS(@RequestBody SMSRequest smsRequest){
        log.info("In SMSController | postSendSMS");
        return smsService.sendSMS(smsRequest);
    }


}
