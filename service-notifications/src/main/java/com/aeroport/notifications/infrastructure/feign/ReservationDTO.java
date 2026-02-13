package com.aeroport.notifications.infrastructure.feign;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long id;
    private Long passagerId;
    private Long volId;
}