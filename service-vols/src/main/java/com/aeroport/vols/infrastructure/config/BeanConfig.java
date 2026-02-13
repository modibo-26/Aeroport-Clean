package com.aeroport.vols.infrastructure.config;

import com.aeroport.vols.application.VolUseCaseImpl;
import com.aeroport.vols.domain.port.in.VolUseCase;
import com.aeroport.vols.domain.port.out.VolEventPublisherPort;
import com.aeroport.vols.domain.port.out.VolRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public VolUseCase volUseCase(VolRepositoryPort repositoryPort,
                                  VolEventPublisherPort eventPublisherPort) {
        return new VolUseCaseImpl(repositoryPort, eventPublisherPort);
    }
}