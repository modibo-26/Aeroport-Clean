package com.aeroport.notifications.presentation;

import com.aeroport.notifications.domain.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationPresentationMapper {

    public NotificationResponseDto toResponseDto(Notification domain) {
        return NotificationResponseDto.builder()
                .id(domain.getId())
                .passagerId(domain.getPassagerId())
                .volId(domain.getVolId())
                .reservationId(domain.getReservationId())
                .message(domain.getMessage())
                .dateCreation(domain.getDateCreation())
                .lue(domain.getLue())
                .build();
    }

    public Notification toDomain(NotificationResponseDto dto) {
        return Notification.builder()
                .id(dto.getId())
                .passagerId(dto.getPassagerId())
                .volId(dto.getVolId())
                .reservationId(dto.getReservationId())
                .message(dto.getMessage())
                .dateCreation(dto.getDateCreation())
                .lue(dto.getLue())
                .build();
    }
}