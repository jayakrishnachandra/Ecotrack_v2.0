package com.Ecotrack.IotService.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.Ecotrack.IotService.Models.KafkaUsageEntity;

@Service
public class KafkaMessagePublisher {    
    @Autowired
    private KafkaTemplate<String, KafkaUsageEntity> template;

    public void sendMessage(KafkaUsageEntity ka) {
        template.send("quickstart-events", ka);
    }
}
