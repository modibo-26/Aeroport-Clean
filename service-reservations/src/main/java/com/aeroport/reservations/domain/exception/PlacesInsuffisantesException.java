package com.aeroport.reservations.domain.exception;

public class PlacesInsuffisantesException extends RuntimeException {
    public PlacesInsuffisantesException() {
        super("Pas assez de places disponible");
    }
}
