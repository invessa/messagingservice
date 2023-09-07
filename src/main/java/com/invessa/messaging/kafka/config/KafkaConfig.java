package com.invessa.messaging.kafka.config;

import com.invessa.messaging.request.NotificationRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${invessa.kafka.notification.topic}")
//    public static String NOTIFICATION_TOPIC;
//
//    @Bean
//    public KafkaTemplate<String, NotificationRequest> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ProducerFactory<String, NotificationRequest> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        // HashMap to store the configurations
//        Map<String, Object> map = new HashMap<>();
//
//        // put the host IP in the map
//        map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//
//        // put the group ID in the map
//        map.put(ConsumerConfig.GROUP_ID_CONFIG, "id");
//        map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        return new DefaultKafkaConsumerFactory<>(map);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListner()
//    {
//        ConcurrentKafkaListenerContainerFactory<String, String> obj = new ConcurrentKafkaListenerContainerFactory<>();
//        obj.setConsumerFactory(consumerFactory());
//        return obj;
//    }
//
//    @Bean
//    public NewTopic topic() {
//        return new NewTopic("smsTopic", 2, (short) 1);
//    }



}
