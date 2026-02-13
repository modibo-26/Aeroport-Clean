package com.aeroport.notifications.infrastructure.kafka;

import com.aeroport.notifications.domain.model.Notification;
import com.aeroport.notifications.domain.port.in.NotificationUseCase;
import com.aeroport.notifications.domain.port.out.ReservationServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

    private final NotificationUseCase notificationUseCase;
    private final ReservationServicePort reservationServicePort;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "reservation-events")
    public void consumeReservation(String message) throws Exception {
        ReservationEvent event = objectMapper.readValue(message, ReservationEvent.class);

        Notification notification = Notification.builder()
                .passagerId(event.getPassagerId())
                .volId(event.getVolId())
                .reservationId(event.getReservationId())
                .message(event.getMessage())
                .build();

        notificationUseCase.addNotification(notification);
    }

    @KafkaListener(topics = "vol-events")
    public void consumeVol(String message) throws Exception {
        VolEvent event = objectMapper.readValue(message, VolEvent.class);

        Set<Long> passagerIds = reservationServicePort.getPassagerIdsByVolId(event.getVolId());

        for (Long passagerId : passagerIds) {
            Notification notification = Notification.builder()
                    .passagerId(passagerId)
                    .volId(event.getVolId())
                    .message(event.getMessage())
                    .build();
            notificationUseCase.addNotification(notification);
        }
    }
}