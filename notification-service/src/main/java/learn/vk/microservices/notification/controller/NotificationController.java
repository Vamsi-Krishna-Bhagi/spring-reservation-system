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

    @GetMapping("/{id}")
    public NotificationDto getInventory(@PathVariable Long id) {
        return notificationService.getInventoryByProductId(id);
    }

    @PutMapping
    public NotificationDto createInventory(@RequestBody NotificationDto notificationDto) {
        return notificationService.updateInventoryItem(notificationDto);
    }

    @PostMapping
    public NotificationDto updateInventory(@RequestBody NotificationDto notificationDto) {
        return notificationService.createInventoryItem(notificationDto);
    }
}
