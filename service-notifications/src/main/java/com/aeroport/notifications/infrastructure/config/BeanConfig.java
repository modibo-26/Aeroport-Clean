package com.aeroport.notifications.infrastructure.config;

import com.aeroport.notifications.application.NotificationUseCaseImpl;
import com.aeroport.notifications.domain.port.in.NotificationUseCase;
import com.aeroport.notifications.domain.port.out.NotificationRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public NotificationUseCase notificationUseCase(NotificationRepositoryPort repositoryPort) {
        return new NotificationUseCaseImpl(repositoryPort);
    }
}