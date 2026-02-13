package com.aeroport.notifications.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long passagerId;
    private Long volId;
    private Long reservationId;
    private String message;
    private LocalDateTime dateCreation;
    private Boolean lue;
}