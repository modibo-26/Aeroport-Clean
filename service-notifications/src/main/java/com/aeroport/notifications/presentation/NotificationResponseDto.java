package com.aeroport.notifications.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;
    private Long passagerId;
    private Long volId;
    private Long reservationId;
    private String message;
    private LocalDateTime dateCreation;
    private Boolean lue;
}