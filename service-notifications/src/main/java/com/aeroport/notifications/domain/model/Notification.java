package com.aeroport.notifications.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private Long id;
    private Long passagerId;
    private Long volId;
    private Long reservationId;
    private String message;
    private LocalDateTime dateCreation;
    private Boolean lue;
}