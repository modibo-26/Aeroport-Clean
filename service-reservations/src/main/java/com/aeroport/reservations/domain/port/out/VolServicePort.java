package com.aeroport.reservations.domain.port.out;

public interface VolServicePort {
    int getPlacesDisponibles(Long volId);
    void addPlaces(Long volId, int places);
    void removePlaces(Long volId, int places);
}
