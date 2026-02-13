package com.aeroport.notifications.domain.port.out;

import java.util.Set;

public interface ReservationServicePort {
    Set<Long> getPassagerIdsByVolId(Long volId);
}