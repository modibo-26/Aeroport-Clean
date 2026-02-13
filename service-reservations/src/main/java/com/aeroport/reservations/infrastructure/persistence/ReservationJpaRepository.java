package com.aeroport.reservations.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
    List<ReservationJpaEntity> findByPassagerId(Long passagerId);
    List<ReservationJpaEntity> findByVolId(Long volId);
}
