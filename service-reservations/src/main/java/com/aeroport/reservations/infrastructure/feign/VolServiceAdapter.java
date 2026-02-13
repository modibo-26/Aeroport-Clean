package com.aeroport.reservations.infrastructure.feign;

import com.aeroport.reservations.domain.port.out.VolServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VolServiceAdapter implements VolServicePort {

    private final VolClient volClient;

    @Override
    public int getPlacesDisponibles(Long volId) {
        return volClient.getPlacesDisponibles(volId);
    }

    @Override
    public void addPlaces(Long volId, int places) {
        volClient.addPlaces(volId, places);
    }

    @Override
    public void removePlaces(Long volId, int places) {
        volClient.removePlaces(volId, places);
    }
}
