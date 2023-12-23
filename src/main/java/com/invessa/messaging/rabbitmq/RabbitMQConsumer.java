package com.invessa.messaging.rabbitmq;

import com.invessa.messaging.email.service.EmailSenderService;
import com.invessa.messaging.enums.NotificationEnumTypes;
import com.invessa.messaging.request.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
@Slf4j
public class RabbitMQConsumer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    EmailSenderService emailSenderService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        log.info(String.format("Received message -> %s", message));
        NotificationRequest notificationRequest = gson.fromJson(message,NotificationRequest.class);
        if(notificationRequest.getChannel().equals(NotificationEnumTypes.EMAIL)){
            emailSenderService.sendEmail(notificationRequest);
        }
    }
}
