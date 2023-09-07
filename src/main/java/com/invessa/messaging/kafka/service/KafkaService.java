package com.invessa.messaging.kafka.service;

import com.invessa.messaging.request.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.invessa.messaging.kafka.config.KafkaConfig;

@Service
public class KafkaService {

    @Autowired
    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    @Autowired
    private final ConsumerFactory<String, String> consumerFactory;

    @Autowired
    public KafkaService(KafkaTemplate<String, NotificationRequest> kafkaTemplate,
                        ConsumerFactory<String, String> consumerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.consumerFactory = consumerFactory;
    }

    public void createTopic(String topicName) {
        // Logic to create a new Kafka topic
        // This implementation will depend on the version of Kafka you are using
        // For Kafka 2.0 and above, you can use AdminClient to create topics.
    }

    public void sendMessage(String topicName, NotificationRequest notificationRequest) {
        kafkaTemplate.send(topicName, notificationRequest);
    }

    public void sendNotificationMessage(NotificationRequest notificationRequest){
        kafkaTemplate.send("smsTopic",notificationRequest);
    }

    // Annotation required to listen the
    // message from kafka server
//    @KafkaListener(topics = "StringProducer", groupId = "id")
//    public void publish(String message)
//    {
//        System.out.println("You have a new message: " + message);
//    }

//    public void readMessages(String topicName) {
////        Properties consumerProps = new Properties();
////        consumerProps.setProperty("bootstrap.servers", "localhost:9092");
////        consumerProps.setProperty("group.id", "my-consumer-group");
//
//        Map<String,Object> consumerMap = new HashMap<>();
//        consumerMap.put("bootstrap.servers", "localhost:9092");
//        consumerMap.put("group.id", "my-consumer-group");
//
//        Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(consumerMap)
//                .createConsumer();
//        consumer.subscribe(Collections.singletonList(topicName));
//
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//            for (ConsumerRecord<String, String> record : records) {
//                System.out.println("Received message: " + record.value());
//            }
//        }
//    }
}
