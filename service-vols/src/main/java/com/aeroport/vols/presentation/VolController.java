package com.aeroport.vols.presentation;

import com.aeroport.vols.domain.model.Statut;
import com.aeroport.vols.domain.model.Vol;
import com.aeroport.vols.domain.port.in.VolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vols")
public class VolController {

    private final VolUseCase volUseCase;
    private final VolPresentationMapper mapper;

    @PostMapping
    public VolResponseDto addVol(@RequestBody VolRequestDto dto) {
        Vol vol = mapper.toDomain(dto);
        Vol saved = volUseCase.AddVol(vol);
        return mapper.toResponseDto(saved);
    }

    @GetMapping
    public List<VolResponseDto> findAll() {
        return volUseCase.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public VolResponseDto findById(@PathVariable Long id) {
        return mapper.toResponseDto(volUseCase.findById(id));
    }

    @PutMapping("/{id}")
    public VolResponseDto editVol(@PathVariable Long id, @RequestBody VolRequestDto dto) {
        Vol vol = mapper.toDomain(dto);
        Vol updated = volUseCase.editVol(id, vol);
        return mapper.toResponseDto(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteVol(@PathVariable Long id) {
        volUseCase.deleteVol(id);
    }

    @GetMapping("/{id}/places-disponibles")
    public int placeDisponible(@PathVariable Long id) {
        return volUseCase.placeDisponible(id);
    }

    @PutMapping("/{id}/statut")
    public VolResponseDto updateStatut(@PathVariable Long id, @RequestBody Statut statut) {
        return mapper.toResponseDto(volUseCase.updateStatut(id, statut));
    }

    @GetMapping("/destination/{destination}")
    public List<VolResponseDto> getByDestination(@PathVariable String destination) {
        return volUseCase.findByDestination(destination).stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @PutMapping("/{id}/add/{places}")
    public VolResponseDto addPlaces(@PathVariable Long id, @PathVariable int places) {
        return mapper.toResponseDto(volUseCase.updatePlaces(id, places));
    }

    @PutMapping("/{id}/remove/{places}")
    public VolResponseDto removePlaces(@PathVariable Long id, @PathVariable int places) {
        return mapper.toResponseDto(volUseCase.updatePlaces(id, -places));
    }
}
