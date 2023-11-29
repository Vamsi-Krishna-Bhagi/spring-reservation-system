package learn.vk.microservices.notification.service;

import learn.vk.microservices.notification.dto.NotificationDto;
import learn.vk.microservices.notification.entity.Notification;
import learn.vk.microservices.notification.exception.NotFoundException;
import learn.vk.microservices.notification.repository.NotificationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto sendNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationDto, notification);

        notification = notificationRepository.save(notification);
        notificationDto.setId(notification.getId());
        return notificationDto;
    }
}
