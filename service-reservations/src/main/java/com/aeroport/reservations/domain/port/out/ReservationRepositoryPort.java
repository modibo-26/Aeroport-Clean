package com.aeroport.reservations.domain.port.out;

import com.aeroport.reservations.domain.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    List<Reservation> findByPassagerId(Long passagerId);
    List<Reservation> findByVolId(Long volId);
}
