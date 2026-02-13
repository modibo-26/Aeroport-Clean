package com.aeroport.notifications.infrastructure.persistence;

import com.aeroport.notifications.domain.model.Notification;
import com.aeroport.notifications.domain.port.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final NotificationJpaRepository jpaRepository;
    private final NotificationMapper mapper;

    @Override
    public Notification save(Notification notification) {
        NotificationJpaEntity entity = mapper.toJpaEntity(notification);
        NotificationJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Notification> findByPassagerId(Long passagerId) {
        return jpaRepository.findByPassagerIdOrderByDateCreationDesc(passagerId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Notification> findByVolId(Long volId) {
        return jpaRepository.findByVolId(volId)
                .stream().map(mapper::toDomain).toList();
    }
}