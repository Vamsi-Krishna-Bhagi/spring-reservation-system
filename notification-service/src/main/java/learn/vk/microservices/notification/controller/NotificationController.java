package learn.vk.microservices.notification.controller;

import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public Notification sendNotification(@RequestBody Notification notification) {
        return notificationService.sendNotification(notification);
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {
        return notificationService.getNotification(id);
    }
}
 