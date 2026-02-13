package com.aeroport.notifications.infrastructure.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEvent {
    private Long reservationId;
    private Long volId;
    private Long passagerId;
    private String type;
    private String message;
}