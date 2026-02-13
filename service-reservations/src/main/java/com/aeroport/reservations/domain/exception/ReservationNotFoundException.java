package com.aeroport.reservations.domain.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Reservation non trouvée");
    }
}
