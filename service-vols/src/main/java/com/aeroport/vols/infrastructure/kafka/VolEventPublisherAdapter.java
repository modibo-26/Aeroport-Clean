package com.aeroport.vols.infrastructure.kafka;

import com.aeroport.vols.domain.port.out.VolEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VolEventPublisherAdapter implements VolEventPublisherPort {

    private final KafkaTemplate<String, VolEvent> kafkaTemplate;

    private static final String TOPIC = "vol-events";

    @Override
    public void publierModification(Long volId, String type, String message) {
        kafkaTemplate.send(TOPIC, new VolEvent(volId, type, message));
    }
}