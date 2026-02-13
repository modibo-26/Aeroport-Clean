package com.aeroport.reservations.infrastructure.persistence;

import com.aeroport.reservations.domain.model.Reservation;
import com.aeroport.reservations.domain.port.out.ReservationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final ReservationJpaRepository jpaRepository;
    private final ReservationMapper mapper;

    @Override
    public Reservation save(Reservation reservation) {
        ReservationJpaEntity entity = mapper.toJpaEntity(reservation);
        ReservationJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Reservation> findByPassagerId(Long passagerId) {
        return jpaRepository.findByPassagerId(passagerId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByVolId(Long volId) {
        return jpaRepository.findByVolId(volId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
