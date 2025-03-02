package com.Ecotrack.UsageService.Services;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.Ecotrack.UsageService.Service.Models.KafkaUsageEntity;

@Service
public class UsageListenerService {

    @KafkaListener(topics = "quickstart-events", groupId = "usage_group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(KafkaUsageEntity usageEntity) {
        // Process the KafkaUsageEntity message
        System.out.println("Received message: " + usageEntity);
        // Add your processing logic here
    }
}
