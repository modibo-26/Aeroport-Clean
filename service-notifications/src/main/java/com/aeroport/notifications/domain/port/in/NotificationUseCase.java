package com.aeroport.notifications.domain.port.in;

import com.aeroport.notifications.domain.model.Notification;
import java.util.List;

public interface NotificationUseCase {
    Notification addNotification(Notification notification);
    List<Notification> findByPassagerId(Long passagerId);
    Notification marquerLue(Long id);
    void deleteNotification(Long id);
    List<Notification> findByVolId(Long volId);
}