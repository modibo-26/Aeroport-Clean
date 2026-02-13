package com.aeroport.reservations.infrastructure.feign;

import lombok.Data;

@Data
public class VolDTO {
    private Long id;
    private int placesDisponibles;
}
