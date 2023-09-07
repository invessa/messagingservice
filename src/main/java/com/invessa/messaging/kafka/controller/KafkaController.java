package com.invessa.messaging.kafka.controller;

import com.invessa.messaging.kafka.producer.MessageProducer;
//import com.invessa.messaging.kafka.service.KafkaService;
import com.invessa.messaging.request.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messaging")
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody NotificationRequest notificationRequest) {
        messageProducer.sendMessage("notifications-1", notificationRequest);
        return "Message sent: " + notificationRequest.toString();
    }
//    public KafkaController(KafkaTemplate<String, NotificationRequest> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }


    //private final KafkaService kafkaService;

//    @Autowired
//    public KafkaController(KafkaService kafkaService) {
//        this.kafkaService = kafkaService;
//    }

//    @PostMapping("/topics/{topicName}")
//    public void createTopic(@PathVariable String topicName) {
//        kafkaService.createTopic(topicName);
//    }

//    @PostMapping("/messages/{topicName}")
//    public void sendMessage(@PathVariable String topicName, @RequestBody NotificationRequest notificationRequest) {
//        kafkaService.sendMessage(topicName, notificationRequest);
//    }

//    @PostMapping("/messages/{topicName}")
//    public String sendMessage(@PathVariable String topicName, @RequestBody NotificationRequest notificationRequest) {
//        this.kafkaTemplate.send("smsTopic",notificationRequest);
//        return "Hey";
//    }

//    @PostMapping("/notifications")
//    public void postSendNotifications(@RequestBody NotificationRequest notificationRequest){
//        kafkaService.sendNotificationMessage(notificationRequest);
//    }

//    @GetMapping("/messages/{topicName}")
//    public void readMessages(@PathVariable String topicName) {
//        kafkaService.readMessages(topicName);
//    }
}
