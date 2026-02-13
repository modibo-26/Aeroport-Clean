package com.aeroport.notifications.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, Long> {
    List<NotificationJpaEntity> findByPassagerIdOrderByDateCreationDesc(Long passagerId);
    List<NotificationJpaEntity> findByVolId(Long volId);
}