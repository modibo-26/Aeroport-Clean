package com.aeroport.reservations.presentation;

import com.aeroport.reservations.domain.model.Reservation;
import com.aeroport.reservations.domain.port.in.ReservationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationUseCase reservationUseCase;
    private final ReservationPresentationMapper mapper;

    @PostMapping
    public ReservationResponseDto addReservation(@RequestBody ReservationRequestDto dto) {
        Reservation reservation = mapper.toDomain(dto);
        Reservation saved = reservationUseCase.addReservation(reservation);
        return mapper.toResponseDto(saved);
    }

    @GetMapping("/{id}")
    public ReservationResponseDto findById(@PathVariable Long id) {
        return mapper.toResponseDto(reservationUseCase.findById(id));
    }

    @PutMapping("/{id}/annuler")
    public ReservationResponseDto annulerReservation(@PathVariable Long id) {
        return mapper.toResponseDto(reservationUseCase.annulerReservation(id));
    }

    @GetMapping("/passager/{passagerId}")
    public List<ReservationResponseDto> findByPassager(@PathVariable Long passagerId) {
        return reservationUseCase.findByPassager(passagerId).stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @PutMapping("/{id}/confirmer")
    public ReservationResponseDto confirmerReservation(@PathVariable Long id) {
        return mapper.toResponseDto(reservationUseCase.confirmerReservation(id));
    }

    @GetMapping("/vol/{volId}")
    public List<ReservationResponseDto> findByVol(@PathVariable Long volId) {
        return reservationUseCase.findByVol(volId).stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
