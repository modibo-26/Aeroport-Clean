package com.aeroport.reservations.infrastructure.kafka;

import com.aeroport.reservations.domain.port.out.ReservationEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationEventPublisherAdapter implements ReservationEventPublisherPort {

    private final KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    private static final String TOPIC = "reservation-events";

    @Override
    public void publier(Long reservationId, Long volId, Long passagerId, String type, String message) {
        kafkaTemplate.send(TOPIC, new ReservationEvent(reservationId, volId, passagerId, type, message));
    }
}
