package com.aeroport.vols.presentation;

import com.aeroport.vols.domain.model.Vol;
import org.springframework.stereotype.Component;

@Component
public class VolPresentationMapper {

    public VolResponseDto toResponseDto(Vol vol) {
        return VolResponseDto.builder()
                .id(vol.getId())
                .numeroVol(vol.getNumeroVol())
                .origine(vol.getOrigine())
                .destination(vol.getDestination())
                .dateDepart(vol.getDateDepart())
                .dateArrivee(vol.getDateArrivee())
                .placesDisponibles(vol.getPlacesDisponibles())
                .prixBase(vol.getPrixBase())
                .compagnie(vol.getCompagnie())
                .statut(vol.getStatut() != null ? vol.getStatut().name() : null)
                .build();
    }

    public Vol toDomain(VolRequestDto dto) {
        return Vol.builder()
                .numeroVol(dto.getNumeroVol())
                .origine(dto.getOrigine())
                .destination(dto.getDestination())
                .dateDepart(dto.getDateDepart())
                .dateArrivee(dto.getDateArrivee())
                .placesDisponibles(dto.getPlacesDisponibles())
                .prixBase(dto.getPrixBase())
                .compagnie(dto.getCompagnie())
                .build();
    }
}