package com.aeroport.vols.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolRequestDto {
    private String numeroVol;
    private String origine;
    private String destination;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private int placesDisponibles;
    private Double prixBase;
    private String compagnie;
}