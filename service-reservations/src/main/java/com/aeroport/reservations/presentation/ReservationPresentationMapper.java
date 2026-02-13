package com.aeroport.reservations.presentation;

import com.aeroport.reservations.domain.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationPresentationMapper {

    public ReservationResponseDto toResponseDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .passagerId(reservation.getPassagerId())
                .volId(reservation.getVolId())
                .dateReservation(reservation.getDateReservation())
                .statut(reservation.getStatut() != null ? reservation.getStatut().name() : null)
                .nombrePlaces(reservation.getNombrePlaces())
                .build();
    }

    public Reservation toDomain(ReservationRequestDto dto) {
        return Reservation.builder()
                .passagerId(dto.getPassagerId())
                .volId(dto.getVolId())
                .nombrePlaces(dto.getNombrePlaces())
                .build();
    }
}
