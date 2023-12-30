package com.invessa.messaging.controller;

import com.invessa.messaging.rabbitmq.RabbitMQProducer;
import com.invessa.messaging.request.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MessagingController {

    private final RabbitMQProducer producer;

    public MessagingController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/message")
    public ResponseEntity<?> postSendMessageToQueue(@RequestBody NotificationRequest notificationRequest){
      log.info("In MessagingController || postSendMessageToQueue");
      producer.sendMessage(notificationRequest);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }

    // http://localhost:8080/api/v1/publish?message=hello
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }

    @GetMapping(value="/check")
    public ResponseEntity<?>getStatus(){
        HashMap<String,String> respMsg = new HashMap<>();
        respMsg.put("status","OK");
        return new ResponseEntity<>(respMsg, HttpStatus.OK);
    }


}
