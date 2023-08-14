package com.invessa.messaging.email.controller;

import com.invessa.messaging.email.request.EmailRequest;
import com.invessa.messaging.email.service.EmailSenderService;
import com.invessa.messaging.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/send")
    public ResponseEntity<?>postSendEmail(@RequestBody EmailRequest emailRequest){
        log.info("In EmailController || postSendEmail");
        log.info("Calling EmailSenderService");
        return emailSenderService.sendEmail(emailRequest);
    }

}
