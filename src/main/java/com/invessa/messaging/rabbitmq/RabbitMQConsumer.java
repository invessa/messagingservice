package com.invessa.messaging.rabbitmq;

import com.invessa.messaging.email.service.EmailSenderService;
import com.invessa.messaging.enums.NotificationEnumTypes;
import com.invessa.messaging.request.NotificationRequest;
import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.service.SMSService;
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

    @Autowired
    SMSService smsService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        log.info(String.format("Received message -> %s", message));
        NotificationRequest notificationRequest = gson.fromJson(message,NotificationRequest.class);
        String vChannel = notificationRequest.getChannel().toString();
        log.info("Channel is :: {}",vChannel);
        if(notificationRequest.getChannel().toString().equals(NotificationEnumTypes.EMAIL.toString())){
            log.info("In Email");
            emailSenderService.sendEmail(notificationRequest);
        }
        if(vChannel.equals("SMS")){
            log.info("In SMS");
            SMSRequest smsRequest = SMSRequest.builder()
                    .sms_text(notificationRequest.getSms_message())
                    .phone_number(notificationRequest.getPhone_number())
                    .build();
            smsService.sendSMS(smsRequest);
        }
    }
}
