package com.aeroport.vols.application;

import com.aeroport.vols.domain.exception.VolNotFoundException;
import com.aeroport.vols.domain.model.Statut;
import com.aeroport.vols.domain.model.Vol;
import com.aeroport.vols.domain.port.in.VolUseCase;
import com.aeroport.vols.domain.port.out.VolEventPublisherPort;
import com.aeroport.vols.domain.port.out.VolRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VolUseCaseImpl implements VolUseCase {

    private final VolRepositoryPort repositoryPort;
    private final VolEventPublisherPort eventPublisherPort;

    @Override
    public Vol AddVol(Vol vol) {
        vol.setStatut(Statut.A_L_HEURE);
        return repositoryPort.save(vol);
    }

    @Override
    public List<Vol> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Vol findById(Long id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new VolNotFoundException(id));
    }

    @Override
    public Vol editVol(Long id, Vol volModifie) {
        Vol vol = findById(id);
        vol.setNumeroVol(volModifie.getNumeroVol());
        vol.setOrigine(volModifie.getOrigine());
        vol.setDestination(volModifie.getDestination());
        vol.setDateDepart(volModifie.getDateDepart());
        vol.setDateArrivee(volModifie.getDateArrivee());
        vol.setPlacesDisponibles(volModifie.getPlacesDisponibles());
        vol.setPrixBase(volModifie.getPrixBase());
        vol.setCompagnie(volModifie.getCompagnie());
        Vol saved = repositoryPort.save(vol);
        eventPublisherPort.publierModification(id, "MODIFIE", "Vol modifié");
        return saved;
    }

    @Override
    public void deleteVol(Long id) {
        repositoryPort.deleteById(id);
    }

    @Override
    public int placeDisponible(Long id) {
        return findById(id).getPlacesDisponibles();
    }

    @Override
    public Vol updateStatut(Long id, Statut statut) {
        Vol vol = findById(id);
        vol.setStatut(statut);
        Vol saved = repositoryPort.save(vol);
        eventPublisherPort.publierModification(id, statut.name(), "Statut changé : " + statut);
        return saved;
    }

    @Override
    public List<Vol> findByDestination(String destination) {
        return repositoryPort.findByDestination(destination);
    }

    @Override
    public Vol updatePlaces(Long id, int places) {
        Vol vol = findById(id);
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() + places);
        return repositoryPort.save(vol);
    }
}
