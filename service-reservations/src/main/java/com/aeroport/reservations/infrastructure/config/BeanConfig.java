package com.aeroport.reservations.infrastructure.config;

import com.aeroport.reservations.application.ReservationUseCaseImpl;
import com.aeroport.reservations.domain.port.in.ReservationUseCase;
import com.aeroport.reservations.domain.port.out.ReservationEventPublisherPort;
import com.aeroport.reservations.domain.port.out.ReservationRepositoryPort;
import com.aeroport.reservations.domain.port.out.VolServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ReservationUseCase reservationUseCase(ReservationRepositoryPort repositoryPort,
                                                  VolServicePort volServicePort,
                                                  ReservationEventPublisherPort eventPublisherPort) {
        return new ReservationUseCaseImpl(repositoryPort, volServicePort, eventPublisherPort);
    }
}
