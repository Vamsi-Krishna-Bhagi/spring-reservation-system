package learn.vk.microservices.notification.service;

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

    public Notification sendNotification(Notification notification) {
        notification = notificationRepository.save(notification);

        return notification;
    }

    public Notification getNotification(Long id) {

        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification not found"));
    }
}
