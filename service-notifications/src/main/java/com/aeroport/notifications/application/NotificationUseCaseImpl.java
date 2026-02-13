package com.aeroport.notifications.application;

import com.aeroport.notifications.domain.exception.NotificationNotFoundException;
import com.aeroport.notifications.domain.model.Notification;
import com.aeroport.notifications.domain.port.in.NotificationUseCase;
import com.aeroport.notifications.domain.port.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class NotificationUseCaseImpl implements NotificationUseCase {

    private final NotificationRepositoryPort repositoryPort;

    @Override
    public Notification addNotification(Notification notification) {
        notification.setLue(false);
        notification.setDateCreation(LocalDateTime.now());
        return repositoryPort.save(notification);
    }

    @Override
    public List<Notification> findByPassagerId(Long passagerId) {
        return repositoryPort.findByPassagerId(passagerId);
    }

    @Override
    public Notification marquerLue(Long id) {
        Notification notification = repositoryPort.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        notification.setLue(true);
        return repositoryPort.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        repositoryPort.deleteById(id);
    }

    @Override
    public List<Notification> findByVolId(Long volId) {
        return repositoryPort.findByVolId(volId);
    }
}