package com.aeroport.reservations.application;

import com.aeroport.reservations.domain.exception.PlacesInsuffisantesException;
import com.aeroport.reservations.domain.exception.ReservationNotFoundException;
import com.aeroport.reservations.domain.model.Reservation;
import com.aeroport.reservations.domain.model.Statut;
import com.aeroport.reservations.domain.port.in.ReservationUseCase;
import com.aeroport.reservations.domain.port.out.ReservationEventPublisherPort;
import com.aeroport.reservations.domain.port.out.ReservationRepositoryPort;
import com.aeroport.reservations.domain.port.out.VolServicePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ReservationUseCaseImpl implements ReservationUseCase {

    private final ReservationRepositoryPort repositoryPort;
    private final VolServicePort volServicePort;
    private final ReservationEventPublisherPort eventPublisherPort;

    @Override
    public Reservation addReservation(Reservation reservation) {
        int placesDisponibles = volServicePort.getPlacesDisponibles(reservation.getVolId());

        if (placesDisponibles < reservation.getNombrePlaces()) {
            throw new PlacesInsuffisantesException();
        }

        reservation.setStatut(Statut.EN_ATTENTE);
        reservation.setDateReservation(LocalDateTime.now());

        Reservation saved = repositoryPort.save(reservation);

        volServicePort.removePlaces(reservation.getVolId(), reservation.getNombrePlaces());

        eventPublisherPort.publier(saved.getId(), saved.getVolId(), saved.getPassagerId(),
                "CRÉATION", "Réservation créé");

        return saved;
    }

    @Override
    public Reservation findById(Long id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Override
    public Reservation annulerReservation(Long id) {
        Reservation reservation = findById(id);
        reservation.setStatut(Statut.ANNULEE);
        Reservation saved = repositoryPort.save(reservation);

        volServicePort.addPlaces(reservation.getVolId(), reservation.getNombrePlaces());

        eventPublisherPort.publier(saved.getId(), saved.getVolId(), saved.getPassagerId(),
                "ANNULATION", "Réservation annulée");

        return saved;
    }

    @Override
    public List<Reservation> findByPassager(Long passagerId) {
        return repositoryPort.findByPassagerId(passagerId);
    }

    @Override
    public Reservation confirmerReservation(Long id) {
        Reservation reservation = findById(id);
        reservation.setStatut(Statut.CONFIRMEE);
        Reservation saved = repositoryPort.save(reservation);

        eventPublisherPort.publier(saved.getId(), saved.getVolId(), saved.getPassagerId(),
                "CONFIRMATION", "Réservation confirmeée");

        return saved;
    }

    @Override
    public List<Reservation> findByVol(Long volId) {
        return repositoryPort.findByVolId(volId);
    }
}
