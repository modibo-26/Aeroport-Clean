package com.aeroport.reservations.infrastructure.persistence;

import com.aeroport.reservations.domain.model.Reservation;
import com.aeroport.reservations.domain.model.Statut;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toDomain(ReservationJpaEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .passagerId(entity.getPassagerId())
                .volId(entity.getVolId())
                .dateReservation(entity.getDateReservation())
                .statut(entity.getStatut() != null ? Statut.valueOf(entity.getStatut()) : null)
                .nombrePlaces(entity.getNombrePlaces())
                .build();
    }

    public ReservationJpaEntity toJpaEntity(Reservation domain) {
        return ReservationJpaEntity.builder()
                .id(domain.getId())
                .passagerId(domain.getPassagerId())
                .volId(domain.getVolId())
                .dateReservation(domain.getDateReservation())
                .statut(domain.getStatut() != null ? domain.getStatut().name() : null)
                .nombrePlaces(domain.getNombrePlaces())
                .build();
    }
}
