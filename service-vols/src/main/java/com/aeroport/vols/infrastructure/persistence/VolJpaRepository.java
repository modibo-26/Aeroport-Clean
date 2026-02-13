package com.aeroport.vols.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolJpaRepository extends JpaRepository<VolJpaEntity, Long> {
    List<VolJpaEntity> findByDestination(String destination);
}