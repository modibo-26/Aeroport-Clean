package com.aeroport.notifications.infrastructure.persistence;

import com.aeroport.notifications.domain.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationJpaEntity toJpaEntity(Notification domain) {
        return NotificationJpaEntity.builder()
                .id(domain.getId())
                .passagerId(domain.getPassagerId())
                .volId(domain.getVolId())
                .reservationId(domain.getReservationId())
                .message(domain.getMessage())
                .dateCreation(domain.getDateCreation())
                .lue(domain.getLue())
                .build();
    }

    public Notification toDomain(NotificationJpaEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .passagerId(entity.getPassagerId())
                .volId(entity.getVolId())
                .reservationId(entity.getReservationId())
                .message(entity.getMessage())
                .dateCreation(entity.getDateCreation())
                .lue(entity.getLue())
                .build();
    }
}