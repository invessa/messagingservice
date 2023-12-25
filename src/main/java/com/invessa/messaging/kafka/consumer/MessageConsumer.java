package com.invessa.messaging.kafka.consumer;

import com.invessa.messaging.email.enums.EmailTypesEnum;
import com.invessa.messaging.email.request.EmailRequest;
import com.invessa.messaging.email.service.EmailSenderService;
import com.invessa.messaging.email.service.EmailService;
import com.invessa.messaging.request.NotificationRequest;
import com.invessa.messaging.sms.providers.vanso.service.VansoService;
import com.invessa.messaging.sms.request.SMSRequest;
import com.invessa.messaging.sms.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {
    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    SMSService smsService;
    //@KafkaListener(topics = "notifications-1") // remove this comment if we want to use Kafka
    public void listener(@Payload NotificationRequest notificationRequest, ConsumerRecord<String, NotificationRequest> cr) {
        log.info("Topic [notifications-1] Received message from {} ", notificationRequest.getChannel());
        log.info(cr.toString());
        boolean response;
        String channel = notificationRequest.getChannel().toString();
        switch (channel) {
            case "SMS" -> {
                String phoneNumber = notificationRequest.getPhone_number();
                String smsMessage = notificationRequest.getSms_message();
                log.info("Channel is {} || Phone number is {} || Message is {} ", channel, phoneNumber, smsMessage);
                //send sms
                SMSRequest smsRequest = SMSRequest.builder()
                        .phone_number(phoneNumber)
                        .sms_text(smsMessage)
                        .build();
                //response = SMSService.sendSMSMessage(smsRequest);
                response = smsService.sendSMSMessage(smsRequest);
            }
            case "EMAIL" -> {
                log.info("Channel is {} || details are {} ", channel, notificationRequest.toString());
                // send Email
                EmailRequest emailRequest = EmailRequest.builder()
                        .email_type(notificationRequest.getEmail_type())
                        .first_name(notificationRequest.getFirst_name())
                        .last_name(notificationRequest.getLast_name())
                        .to_address(notificationRequest.getEmail_address())
                        .build();
                response = emailSenderService.sendCustomerRegistrationConfirmationEmail(emailRequest);
            }
            case "CREATE_ACCOUNT" -> {
            }
            default ->
                    log.info("Cannot handle message. Channel is unknown >> {}", notificationRequest.getChannel().toString());
        }
    }
}