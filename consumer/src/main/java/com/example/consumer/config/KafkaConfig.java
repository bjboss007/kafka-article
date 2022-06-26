package com.example.consumer.config;


import com.example.consumer.model.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.broker}")
    private String brokerServer;
//
    @Bean
    public Map<String, Object> chatProducerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerServer);
        configs.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return configs;
    }
//
    @Bean
    public ConsumerFactory<String, Message> chatKafkaTemplate(){
        JsonDeserializer<Message> deserializer = new JsonDeserializer<>(Message.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.example.consumer.model.Message");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(chatProducerFactory(), new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> chatFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatKafkaTemplate());
        return factory;
    }
}
