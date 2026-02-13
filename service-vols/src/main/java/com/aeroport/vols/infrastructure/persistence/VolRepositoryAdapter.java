package com.aeroport.vols.infrastructure.persistence;

import com.aeroport.vols.domain.model.Vol;
import com.aeroport.vols.domain.port.out.VolRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VolRepositoryAdapter implements VolRepositoryPort {

    private final VolJpaRepository jpaRepository;
    private final VolMapper mapper;

    @Override
    public Vol save(Vol vol) {
        VolJpaEntity entity = mapper.toJpaEntity(vol);
        VolJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Vol> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Vol> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Vol> findByDestination(String destination) {
        return jpaRepository.findByDestination(destination).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}