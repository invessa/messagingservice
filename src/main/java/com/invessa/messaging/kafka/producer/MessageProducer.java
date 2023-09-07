package com.invessa.messaging.kafka.producer;

import com.invessa.messaging.request.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void sendMessage(String topic, NotificationRequest notificationRequest) {
        kafkaTemplate.send(topic, notificationRequest);
    }
}
