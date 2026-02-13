package com.aeroport.reservations.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long id;
    private Long passagerId;
    private Long volId;
    private LocalDateTime dateReservation;
    private String statut;
    private int nombrePlaces;
}
