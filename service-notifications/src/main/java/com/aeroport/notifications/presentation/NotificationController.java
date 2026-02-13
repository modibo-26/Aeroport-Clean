package com.aeroport.notifications.presentation;

import com.aeroport.notifications.domain.model.Notification;
import com.aeroport.notifications.domain.port.in.NotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationUseCase notificationUseCase;
    private final NotificationPresentationMapper mapper;

    @PostMapping
    public NotificationResponseDto addNotification(@RequestBody NotificationResponseDto dto) {
        Notification saved = notificationUseCase.addNotification(mapper.toDomain(dto));
        return mapper.toResponseDto(saved);
    }

    @GetMapping("/passager/{passagerId}")
    public List<NotificationResponseDto> findByPassager(@PathVariable Long passagerId) {
        return notificationUseCase.findByPassagerId(passagerId).stream()
                .map(mapper::toResponseDto).toList();
    }

    @PutMapping("/{id}/lue")
    public NotificationResponseDto marqueLue(@PathVariable Long id) {
        return mapper.toResponseDto(notificationUseCase.marquerLue(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        notificationUseCase.deleteNotification(id);
    }

    @GetMapping("/vol/{volId}")
    public List<NotificationResponseDto> findByVol(@PathVariable Long volId) {
        return notificationUseCase.findByVolId(volId).stream()
                .map(mapper::toResponseDto).toList();
    }
}