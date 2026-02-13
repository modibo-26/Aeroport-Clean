package com.aeroport.vols.infrastructure.persistence;

import com.aeroport.vols.domain.model.Statut;
import com.aeroport.vols.domain.model.Vol;
import org.springframework.stereotype.Component;

@Component
public class VolMapper {

    public Vol toDomain(VolJpaEntity entity) {
        return Vol.builder()
                .id(entity.getId())
                .numeroVol(entity.getNumeroVol())
                .origine(entity.getOrigine())
                .destination(entity.getDestination())
                .dateDepart(entity.getDateDepart())
                .dateArrivee(entity.getDateArrivee())
                .placesDisponibles(entity.getPlacesDisponibles())
                .prixBase(entity.getPrixBase())
                .compagnie(entity.getCompagnie())
                .statut(entity.getStatut() != null ? Statut.valueOf(entity.getStatut()) : null)
                .build();
    }

    public VolJpaEntity toJpaEntity(Vol domain) {
        return VolJpaEntity.builder()
                .id(domain.getId())
                .numeroVol(domain.getNumeroVol())
                .origine(domain.getOrigine())
                .destination(domain.getDestination())
                .dateDepart(domain.getDateDepart())
                .dateArrivee(domain.getDateArrivee())
                .placesDisponibles(domain.getPlacesDisponibles())
                .prixBase(domain.getPrixBase())
                .compagnie(domain.getCompagnie())
                .statut(domain.getStatut() != null ? domain.getStatut().name() : null)
                .build();
    }
}