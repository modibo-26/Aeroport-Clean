package com.aeroport.vols.domain.exception;

public class VolNotFoundException extends RuntimeException {
    public VolNotFoundException(Long id) {
        super("Vol non trouvé");
    }
}