// package com.Ecotrack.UsageService.config;
// import java.util.HashMap;
// import java.util.Map;

// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.annotation.EnableKafka;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
// import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
// import org.springframework.kafka.support.serializer.JsonDeserializer;

// import com.Ecotrack.UsageService.Service.Models.KafkaUsageEntity;

// @Configuration
// @EnableKafka
// public class KafkaConsumerConfig {

//     @Bean
//     public ConsumerFactory<String, KafkaUsageEntity> consumerFactory() {
//         Map<String, Object> props = new HashMap<>();
//         props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//         props.put(ConsumerConfig.GROUP_ID_CONFIG, "usage_group");
//         props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//         props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//         props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
//         props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaUsageEntity.class.getName());
//         props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

//         return new DefaultKafkaConsumerFactory<>(props);
//     }

//     @Bean
//     public ConcurrentKafkaListenerContainerFactory<String, KafkaUsageEntity> kafkaListenerContainerFactory() {
//         ConcurrentKafkaListenerContainerFactory<String, KafkaUsageEntity> factory =
//                 new ConcurrentKafkaListenerContainerFactory<>();
//         factory.setConsumerFactory(consumerFactory());
//         return factory;
//     }
// }


