package com.aeroport.vols.domain.port.in;

import com.aeroport.vols.domain.model.Statut;
import com.aeroport.vols.domain.model.Vol;

import java.util.List;

public interface VolUseCase {
    Vol AddVol(Vol vol);
    List<Vol> findAll();
    Vol findById(Long id);
    Vol editVol(Long id, Vol vol);
    void deleteVol(Long id);
    int placeDisponible(Long id);
    Vol updateStatut(Long id, Statut statut);
    List<Vol> findByDestination(String destination);
    Vol updatePlaces(Long id, int places);
}
