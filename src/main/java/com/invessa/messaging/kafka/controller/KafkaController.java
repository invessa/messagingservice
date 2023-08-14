package com.invessa.messaging.kafka.controller;

import com.invessa.messaging.kafka.service.KafkaService;
import com.invessa.messaging.request.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/topics/{topicName}")
    public void createTopic(@PathVariable String topicName) {
        kafkaService.createTopic(topicName);
    }

    @PostMapping("/messages/{topicName}")
    public void sendMessage(@PathVariable String topicName, NotificationRequest notificationRequest) {
        kafkaService.sendMessage(topicName, notificationRequest);
    }

    @PostMapping("/notifications")
    public void postSendNotifications(@RequestBody NotificationRequest notificationRequest){
        kafkaService.sendNotificationMessage(notificationRequest);
    }

//    @GetMapping("/messages/{topicName}")
//    public void readMessages(@PathVariable String topicName) {
//        kafkaService.readMessages(topicName);
//    }
}
