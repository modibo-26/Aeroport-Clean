package com.aeroport.reservations.domain.port.in;

import com.aeroport.reservations.domain.model.Reservation;

import java.util.List;

public interface ReservationUseCase {
    Reservation addReservation(Reservation reservation);
    Reservation findById(Long id);
    Reservation annulerReservation(Long id);
    List<Reservation> findByPassager(Long passagerId);
    Reservation confirmerReservation(Long id);
    List<Reservation> findByVol(Long volId);
}
