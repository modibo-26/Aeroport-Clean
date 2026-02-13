package com.aeroport.notifications.infrastructure.feign;

import com.aeroport.notifications.domain.port.out.ReservationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationServiceAdapter implements ReservationServicePort {

    private final ReservationClient reservationClient;

    @Override
    public Set<Long> getPassagerIdsByVolId(Long volId) {
        List<ReservationDTO> reservations = reservationClient.getReservation(volId);
        return reservations.stream()
                .map(ReservationDTO::getPassagerId)
                .collect(Collectors.toSet());
    }
}