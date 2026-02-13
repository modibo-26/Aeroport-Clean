package com.aeroport.reservations.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long id;
    private Long passagerId;
    private Long volId;
    private LocalDateTime dateReservation;
    private Statut statut;
    private int nombrePlaces;
}
