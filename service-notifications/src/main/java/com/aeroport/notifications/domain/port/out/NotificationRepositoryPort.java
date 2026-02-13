package com.aeroport.notifications.domain.port.out;

import com.aeroport.notifications.domain.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);
    Optional<Notification> findById(Long id);
    void deleteById(Long id);
    List<Notification> findByPassagerId(Long passagerId);
    List<Notification> findByVolId(Long volId);
}