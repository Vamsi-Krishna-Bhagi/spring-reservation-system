package learn.vk.microservices.notification.controller;

import learn.vk.microservices.notification.dto.NotificationDto;
import learn.vk.microservices.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public NotificationDto sendNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.sendNotification(notificationDto);
    }
}
 