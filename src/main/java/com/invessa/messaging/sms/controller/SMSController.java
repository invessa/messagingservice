package com.invessa.messaging.sms.controller;

import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class SMSController {


    @PostMapping("/sms/send")
    public ResponseEntity<?>postSendSMS(@RequestBody SMSRequest smsRequest){
        log.info("In SMSController | postSendSMS");
        return SMSService.sendSMS(smsRequest);
    }

    @GetMapping("/test")
    public ResponseEntity<String>test(){
        return new ResponseEntity<>("Ok",HttpStatus.OK);
    }

}
