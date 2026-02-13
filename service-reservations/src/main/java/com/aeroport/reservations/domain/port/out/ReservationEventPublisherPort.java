package com.aeroport.reservations.domain.port.out;

public interface ReservationEventPublisherPort {
    void publier(Long reservationId, Long volId, Long passagerId, String type, String message);
}
