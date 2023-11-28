package learn.vk.microservices.notification.service;

import learn.vk.microservices.notification.dto.NotificationDto;
import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.exception.NotFoundException;
import learn.vk.microservices.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto getInventoryByProductId(Long productId) {
        Notification notification = notificationRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setProductId(notification.getItemId());
        notificationDto.setQuantity(notification.getQuantity());
        return notificationDto;

    }

    public NotificationDto updateInventoryItem(NotificationDto notificationDto) {
        Notification notification = notificationRepository.findById(notificationDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));
        notification.setQuantity(notificationDto.getQuantity());

        notification = notificationRepository.save(notification);
        notificationDto.setQuantity(notification.getQuantity());
        return notificationDto;
    }

    public NotificationDto createInventoryItem(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setItemId(notificationDto.getProductId());
        notification.setQuantity(notificationDto.getQuantity());

        notification = notificationRepository.save(notification);

        notificationDto.setProductId(notification.getItemId());
        return notificationDto;
    }
}
