package com.aeroport.vols.domain.port.out;

public interface VolEventPublisherPort {
    void publierModification(Long volId, String type, String message);
}