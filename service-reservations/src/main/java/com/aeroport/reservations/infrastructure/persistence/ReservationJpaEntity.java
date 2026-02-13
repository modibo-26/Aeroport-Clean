package com.aeroport.reservations.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationJpaEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long passagerId;

    @Column(nullable = false)
    private Long volId;

    private LocalDateTime dateReservation;

    private String statut;

    @Column(nullable = false)
    private int nombrePlaces;
}
