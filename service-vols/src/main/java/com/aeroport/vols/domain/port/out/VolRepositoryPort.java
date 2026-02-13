package com.aeroport.vols.domain.port.out;

import com.aeroport.vols.domain.model.Vol;

import java.util.List;
import java.util.Optional;

public interface VolRepositoryPort {
    Vol save(Vol vol);
    Optional<Vol> findById(Long id);
    List<Vol> findAll();
    List<Vol> findByDestination(String destination);
    void deleteById(Long id);
}