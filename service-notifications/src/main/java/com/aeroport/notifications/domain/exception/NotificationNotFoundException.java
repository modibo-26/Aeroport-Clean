package com.aeroport.notifications.domain.exception;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long id) {
        super("Notification non trouvée : " + id);
    }
}